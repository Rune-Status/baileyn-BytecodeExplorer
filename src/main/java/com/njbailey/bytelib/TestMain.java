package com.njbailey.bytelib;

import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class TestMain {
    public static void main(String[] args) throws IOException {
        JavaApplication javaApplication = new JavaApplication();
        javaApplication.load(Paths.get("C://", "Users", "nicho", "Downloads", "gamepack.jar"));

        System.out.println("Loaded " + javaApplication.getNumLoadedClasses() + " classes.");

        System.out.println("Finding references");

        List<ClassFile> inputClasses = javaApplication.findClasses(classFile -> {
            boolean foundInputStream = false;
            boolean foundOutputStream = false;

            for(Field field : classFile.getFields()) {
                if(field.desc.equals("Ljava/io/InputStream;")) {
                    foundInputStream = true;
                } else if(field.desc.equals("Ljava/io/OutputStream;")) {
                    foundOutputStream = true;
                }
            }

            return foundInputStream && !foundOutputStream;
        });

        List<ClassFile> outputClasses = javaApplication.findClasses(classFile -> {
            boolean foundInputStream = false;
            boolean foundOutputStream = false;

            for(Field field : classFile.getFields()) {
                if(field.desc.equals("Ljava/io/InputStream;")) {
                    foundInputStream = true;
                } else if(field.desc.equals("Ljava/io/OutputStream;")) {
                    foundOutputStream = true;
                }
            }

            return !foundInputStream && foundOutputStream;
        });

        if(inputClasses.size() != 1 || outputClasses.size() != 1) {
            System.out.println("Unable to accurately determine the I/O classes.");
            return;
        }

        ClassFile inputClass = inputClasses.get(0);
        ClassFile outputClass = outputClasses.get(0);

        System.out.println("Input: " + inputClass.name);
        System.out.println("Output: " + outputClass.name);

        List<ClassFile> socketClasses = javaApplication.findClasses(classFile -> {
            boolean foundInputStream = false;
            boolean foundOutputStream = false;

            for(Field field : classFile.getFields()) {
                if(field.desc.equals("L" + inputClass.name + ";")) {
                    foundInputStream = true;
                } else if(field.desc.equals("L" + outputClass.name + ";")) {
                    foundOutputStream = true;
                }
            }

            return foundInputStream && foundOutputStream;
        });

        if(socketClasses.size() != 1) {
            System.out.println("Unable to determine the socket class.");
            return;
        }

        ClassFile socketClass = socketClasses.get(0);
        System.out.println("Socket Class: " + socketClass.name);

        javaApplication.findClasses(classFile -> {
            for(Method method : classFile.getMethods()) {
                InsnList insnList = method.instructions;
                AbstractInsnNode cur = insnList.getFirst();

                while(cur != null) {
                    if(cur instanceof LdcInsnNode) {
                        LdcInsnNode ldc = (LdcInsnNode) cur;

                        if(ldc.cst instanceof Integer) {
                            if((Integer) ldc.cst == 43595) {
                                System.out.println("Found: " + classFile.name + "." + method.name);
                            }
                        }
                    }

                    cur = cur.getNext();
                }
            }

            return false;
        });

        //        javaApplication.searchMethods(method -> {
//            InsnList insnList = method.instructions;
//            AbstractInsnNode cur = insnList.getFirst();
//
//            while(cur != null) {
//                if(cur instanceof MethodInsnNode) {
//                    MethodInsnNode min = (MethodInsnNode) cur;
//
//                    if(min.owner.equals("java/net/Socket")) {
//                        return true;
//                    }
//                }
//
//                cur = cur.getNext();
//            }
//
//            return false;
//        });
    }
}
