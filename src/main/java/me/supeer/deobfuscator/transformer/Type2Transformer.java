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
import me.supeer.deobfuscator.loader.ClassLoader;
import me.supeer.deobfuscator.loader.Clazz;
import org.objectweb.asm.tree.*;

public class Type2Transformer extends AbstractTransformer{

    private String methodName;
    private String methodDesc;

    public Type2Transformer(Content content, String methodName, String methodDesc) {
        super(content);
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public void transform() {
        for(ClassNode classNode : content.classes.values()){
            Clazz clazz = null;
            try{
                clazz = ClassLoader.getInstance().buildClass(classNode);
            }
            catch (Throwable throwable){
                Main.log(throwable.toString());
                continue;
            }
            if(clazz == null){
                Main.log(classNode.name + " was skipped due to couldn't parse Class");
                continue;
            }

            for(MethodNode methodNode : classNode.methods){
                for(AbstractInsnNode insnNode : methodNode.instructions){
                    if(insnNode instanceof MethodInsnNode methodInsnNode){
                        if(methodInsnNode.owner.equals(classNode.name) &&
                                methodInsnNode.name.equals(methodName) &&
                                methodInsnNode.desc.equals(methodDesc)){
                            try{
                                //you can add new parameters
                                String param1 = (String) ((LdcInsnNode)insnNode.getPrevious()).cst;
                                String decrypted = (String) clazz.invoke(methodName, methodDesc, null, param1);
                                Main.log("decrypted string -- " + decrypted);
                                //removing parameters
                                methodNode.instructions.remove(insnNode.getPrevious());
                                //
                                methodNode.instructions.set(insnNode, new LdcInsnNode(decrypted));
                            }
                            catch (Throwable ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }
}
