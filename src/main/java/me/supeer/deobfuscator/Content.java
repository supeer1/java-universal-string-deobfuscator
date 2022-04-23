package me.supeer.deobfuscator;
/*
 *
 * @Author supeer
 *
 *
 * - 2022 -
 *
 */


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class Content {

    public HashMap<String, ClassNode> classes = new HashMap<>();
    public HashMap<String, JarContentFile> assets = new HashMap<>();


    public void save(String path){
        try{
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(new File(path)));
            /*for(JarContentFile jarContentFile : assets.values()){

            }*/

            for(ClassNode classNode : classes.values()){
                try{
                    jarOutputStream.putNextEntry(new JarEntry(classNode.name + ".class"));
                    jarOutputStream.write(Utils.toByteArray(classNode));
                    jarOutputStream.closeEntry();
                }
                catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }


            jarOutputStream.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static final Content load(String input){
        Content content = new Content();
        try{
            var jarFile = new JarFile(new File(input));
            Enumeration<? extends JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement();
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                if(jarEntry.getName().endsWith(".class")){
                    try{
                        ClassReader classReader = new ClassReader(inputStream);
                        ClassNode classNode = new ClassNode();
                        classReader.accept(classNode, 0);
                        content.classes.put(jarEntry.getName(), classNode);
                    }
                    catch (Throwable ex){
                        ex.printStackTrace();
                    }
                    continue;
                }
                content.assets.put(jarEntry.getName(), new JarContentFile(inputStream, jarEntry));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return content;
    }



    public static class JarContentFile {
        public InputStream inputStream;
        public JarEntry entry;

        public JarContentFile(InputStream inputStream, JarEntry entry) {
            this.inputStream = inputStream;
            this.entry = entry;
        }
    }

}
