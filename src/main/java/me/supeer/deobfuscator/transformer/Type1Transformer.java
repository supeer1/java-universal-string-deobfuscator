package me.supeer.deobfuscator.transformer;
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
import me.supeer.deobfuscator.loader.ClassLoader;
import me.supeer.deobfuscator.loader.Clazz;
import org.objectweb.asm.tree.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Type1Transformer extends AbstractTransformer{

    private String className;
    private String methodName;
    private String methodDesc;

    public Type1Transformer(Content content, String className, String methodName, String methodDesc) {
        super(content);
        this.className = className;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public void transform() {
        ClassNode decryptClass = Utils.findClass(content, className);
        if(decryptClass == null){
            throw new RuntimeException("decrypt class couldn't be found");
        }
        MethodNode decryptMethod = Utils.findMethod(decryptClass, methodName, methodDesc);
        if(decryptMethod == null){
            throw new RuntimeException("decrypt method couldn't be found");
        }

        Clazz clazz = ClassLoader.getInstance().buildClass(decryptClass);

        if(clazz == null){
            throw new RuntimeException("decrypt method couldn't be loaded");
        }
        AtomicInteger count = new AtomicInteger();

        for(ClassNode classNode : content.classes.values()){
            for(MethodNode methodNode : classNode.methods){
                for(AbstractInsnNode insnNode : methodNode.instructions){
                    if(insnNode instanceof MethodInsnNode methodInsnNode){
                        if(methodInsnNode.owner.equals(className) && methodInsnNode.name.equals(methodName) &&
                        methodInsnNode.desc.equals(methodDesc)){
                            try{
                                //you can add new parameters
                                String param1 = String.valueOf(((LdcInsnNode)insnNode.getPrevious()).cst);
                                String decrypted = (String)clazz.invoke(methodName, methodDesc, null, param1);;
                                Main.log("decrypted string -- " + decrypted);
                                //removing parameters
                                methodNode.instructions.remove(insnNode.getPrevious());
                                //
                                methodNode.instructions.set(insnNode, new LdcInsnNode(decrypted));
                                count.getAndIncrement();
                            }
                            catch (Throwable ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        System.out.println(count.get() + " strings are decrypted");
    }
}
