package com.njbailey.bytelib;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class JavaApplication {
    private List<ClassFile> classes;

    public JavaApplication() {
        this.classes = new ArrayList<>();
    }

    public JavaApplication(List<ClassFile> classes) {
        this.classes = classes;
    }

    public void load(Path path) throws IOException {
        // If this path is a directory, treat it like it holds the classes to load.
        if(Files.isDirectory(path)) {
            loadClassesFromDirectory(path);
        } else if(path.toString().endsWith(".jar")) {
            loadClassesFromJar(path);
        } else if(path.toString().endsWith(".class")) {
            loadClass(path);
        } else {
            throw new IllegalArgumentException("Unable to load classes from " + path);
        }
    }

    public int getNumLoadedClasses() {
        return classes.size();
    }

    public List<ClassFile> findClasses(Predicate<ClassFile> pred) {
        List<ClassFile> foundClasses = new ArrayList<>();

        for(ClassFile classFile : classes) {
            if(pred.test(classFile)) {
                foundClasses.add(classFile);
            }
        }

        return foundClasses;
    }

    public List<ClassFile> getClasses() {
        return classes;
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
            for (ClassFile classFile : classes) {
                JarEntry entry = new JarEntry(classFile.name + ".class");
                jarOutputStream.putNextEntry(entry);

                ClassWriter writer = new ClassWriter(0);
                classFile.accept(writer);

                jarOutputStream.write(writer.toByteArray());
                jarOutputStream.closeEntry();
            }
        }
    }

    private void loadClassesFromDirectory(Path dir) throws IOException {
        checkArgument(Files.isDirectory(dir), "Argument wasn't a directory.");

        for(Path path : Files.list(dir).collect(Collectors.toList())) {
            if(Files.isDirectory(path)) {
                loadClassesFromDirectory(path);
            } else if(path.toString().endsWith(".class")) {
                loadClass(path);
            }
        }
    }

    private void loadClassesFromJar(Path path) throws IOException {
        checkArgument(path.toString().endsWith(".jar"), "Path wasn't a jar file.");

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
        checkNotNull(inputStream);

        ClassReader reader = new ClassReader(inputStream);
        ClassFile classFile = new ClassFile(this);
        reader.accept(classFile, 0);

        classes.add(classFile);
    }
}
