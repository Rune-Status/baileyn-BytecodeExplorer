package org.objectweb.asm.tree;

import lombok.Getter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

@Getter
public class ApplicationNode {
    private final List<ClassNode> classes;
    private String name;

    public ApplicationNode() {
        this.classes = new ArrayList<>();
    }

    public void load(Path path) throws IOException {
        // If this path is a directory, treat it like it holds the classes to load.
        if(Files.isDirectory(path)) {
            name = path.getFileName().toString();
            loadClassesFromDirectory(path);
        } else if(path.toString().endsWith(".jar")) {
            name = path.getFileName().toString();
            name = name.substring(0, name.length() - 4);

            loadClassesFromJar(path);
        } else if(path.toString().endsWith(".class")) {
            name = path.getFileName().toString();
            name = name.substring(0, name.length() - 6);

            loadClass(path);
        } else {
            throw new IllegalArgumentException("Unable to load classes from " + path);
        }
    }

    /**
     * Save the classes contained in this application to the specified path.
     *
     * Note: If the path already exists it's overwritten. It's up to the calling function
     * to handle any possible existence.
     *
     * @param path the path to save the Application to
     * @throws IOException
     */
    public void save(Path path) throws IOException {
        try(JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(path))) {
            for (ClassNode classNode : classes) {
                JarEntry entry = new JarEntry(classNode.name + ".class");
                jarOutputStream.putNextEntry(entry);

                ClassWriter writer = new ClassWriter(0);
                classNode.accept(writer);

                jarOutputStream.write(writer.toByteArray());
                jarOutputStream.closeEntry();
            }
        }
    }

    protected void renameMethodReferences(MethodNode toRename, String newName) {
        for(ClassNode classNode : classes) {
            for(MethodNode method : classNode.methods) {
                method.renameReferences(toRename, newName);
            }
        }
    }

    protected void renameFieldReferences(FieldNode toRename, String newName) {
        for(ClassNode classNode : classes) {
            for(MethodNode method : classNode.methods) {
                method.renameReferences(toRename, newName);
            }
        }
    }

    public void renameClassReferences(ClassNode toRename, String newName) {
        for(ClassNode classNode : classes) {
            if(classNode.getInterfaces().contains(toRename.name)) {
                classNode.getInterfaces().remove(toRename.name);
                classNode.getInterfaces().add(newName);
            }

            if(classNode.getSuperName().equals(toRename.getName())) {
                classNode.setSuperName(newName);
            }

            for(MethodNode method : classNode.methods) {
                method.renameReferences(toRename, newName);
            }
        }
    }

    private void loadClassesFromDirectory(Path dir) throws IOException {
        if(!Files.isDirectory(dir)) {
            throw new RuntimeException("Argument wasn't a directory.");
        }

        for(Path path : Files.list(dir).collect(Collectors.toList())) {
            if(Files.isDirectory(path)) {
                loadClassesFromDirectory(path);
            } else if(path.toString().endsWith(".class")) {
                loadClass(path);
            }
        }
    }

    private void loadClassesFromJar(Path path) throws IOException {
        if(!path.toString().endsWith(".jar")) {
            throw new RuntimeException("Path wasn't a jar file.");
        }

        JarFile jarFile = new JarFile(path.toFile());
        Enumeration<JarEntry> entries = jarFile.entries();

        while(entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if(entry.getName().endsWith(".class")) {
                loadClass(jarFile.getInputStream(entry));
            }

            // TODO: Handle extra files in the JarFile.
        }
    }

    private void loadClass(Path path) throws IOException {
        loadClass(Files.newInputStream(path));
    }

    private void loadClass(InputStream inputStream) throws IOException {
        if(inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null");
        }

        ClassReader reader = new ClassReader(inputStream);
        ClassNode classNode = new ClassNode(this);
        reader.accept(classNode, 0);

        classes.add(classNode);
    }
}
