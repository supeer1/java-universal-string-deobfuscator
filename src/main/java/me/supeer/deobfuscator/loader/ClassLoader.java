package me.supeer.deobfuscator.loader;
/*
 *
 * @Author supeer
 *
 *
 * - 2022 -
 *
 */


import me.supeer.deobfuscator.Content;
import me.supeer.deobfuscator.Main;
import me.supeer.deobfuscator.Utils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassLoader extends java.lang.ClassLoader {

    private static final Map<String, Clazz> classes = new ConcurrentHashMap<>();


    private static ClassLoader instance;


    public static ClassLoader getInstance(){
        if(instance == null){
            instance = new ClassLoader();
        }
        return instance;
    }

    public Clazz buildClass(ClassNode classNode) {
        if (classes.containsKey(classNode.name.replace('/', '.')))
            return classes.get(classNode.name.replace('/', '.'));

        byte[] bytes = Utils.toByteArray(classNode, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        Clazz clazz = new Clazz(defineClass(classNode.name.replace('/', '.'), bytes, 0, bytes.length, this.getClass().getProtectionDomain()));
        resolveClass(clazz.getClazz());

        classes.put(classNode.name.replace('/', '.'), clazz);
        return clazz;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        name = name.replace('/', '.');
        if (classes.containsKey(name))
            return classes.get(name).getClazz();

        return super.findClass(name);
    }

}
