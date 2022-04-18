package me.supeer.deobfuscator;
/*
 *
 * @Author supeer
 *
 *
 * - 2022 -
 *
 */


import me.supeer.deobfuscator.transformer.AbstractTransformer;
import me.supeer.deobfuscator.transformer.Type1Transformer;
import me.supeer.deobfuscator.transformer.Type2Transformer;
import me.supeer.deobfuscator.transformer.Type3Transformer;

public class Deobfuscator {

    public static final int maxType = 3;
    public static final int minType = 1;

    private static void runType1(Content input, String className, String methodName, String methodDesc){
        AbstractTransformer type1Transformer = new Type1Transformer(input, className, methodName, methodDesc);
        type1Transformer.transform();
    }

    private static void runType2(Content input, String methodName, String methodDesc){
        AbstractTransformer type2Transformer = new Type2Transformer(input, methodName, methodDesc);
        type2Transformer.transform();
    }

    private static void runType3(Content input, String methodDesc, int methodAccess){
        AbstractTransformer type3Transformer = new Type3Transformer(input, methodDesc, methodAccess);
        type3Transformer.transform();
    }

    public static void run(String input, int type){
        Content content = Content.load(input);
        switch (type){
            case 1 ->{
                String className = Main.input("enter decrypt class name");
                String methodName = Main.input("enter decrypt method name");
                String methodDesc = Main.input("enter decrypt method desc");
                runType1(content, className, methodName, methodDesc);
            }
            case 2 ->{
                String methodName = Main.input("enter decrypt method name");
                String methodDesc = Main.input("enter decrypt method desc");
                runType2(content, methodName, methodDesc);
            }
            case 3 ->{
                String methodDesc = Main.input("enter decrypt method desc");
                int methodAccess = Integer.parseInt(Main.input("enter decrypt method access"));
                runType3(content, methodDesc, methodAccess);
            }
        }
        String pathn = input + "deobfuscated-"+(System.currentTimeMillis()/1000)+".jar";
        content.save(pathn);
        Main.log("deobfuscated jar is saved to " + pathn);
    }



}
