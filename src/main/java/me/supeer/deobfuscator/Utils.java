package me.supeer.deobfuscator;
/*
 *
 * @Author supeer
 *
 *
 * - 2022 -
 *
 */


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class Utils {

    public static byte[] toByteArray(ClassNode classNode){
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    public static byte[] toByteArray(ClassNode classNode, int a){
        ClassWriter classWriter = new ClassWriter(a);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }


    public static String[] getParameters(String editedDesc){
        String params = editedDesc.substring(1, editedDesc.lastIndexOf(")"));
        System.out.println(params);
        return params.split("");
    }


    public static ClassNode findClass(Content content, String name){
        return content.classes.values().stream().filter(a -> a.name.equals(name)).findFirst().orElse(null);
    }

    public static MethodNode findMethod(ClassNode classNode, String name){
        return classNode.methods.stream().filter(a -> a.name.equals(name)).findFirst().orElse(null);
    }

    public static MethodNode findMethod(ClassNode classNode, String name, String desc){
        return classNode.methods.stream().filter(a -> a.name.equals(name) && a.desc.equals(desc)).findFirst().orElse(null);
    }

    public static MethodNode findMethod(ClassNode classNode, String desc, int access){
        return classNode.methods.stream().filter(a -> a.access == access && a.desc.equals(desc)).findFirst().orElse(null);
    }



}
