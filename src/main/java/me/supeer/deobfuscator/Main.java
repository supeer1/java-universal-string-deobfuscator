package me.supeer.deobfuscator;
/*
 *
 * @Author supeer
 *
 *
 * - 2022 -
 *
 */


import java.util.Scanner;

public class Main {


    //input.jar type
    public static void main(String ...args){
        if(args.length != 2){
            throw new RuntimeException("undefined arguments");
        }
        String input = args[0];
        String typeAsString = args[1];

        int type = Integer.parseInt(typeAsString);

        if(type < Deobfuscator.minType || type > Deobfuscator.maxType){
            throw new RuntimeException("unacceptable type");
        }

        Deobfuscator.run(input, type);
    }

    public static void log(String msg){
        System.out.println("[UniversalStringDeobfuscator] -> " + msg);
    }

    public static String input(String msg){
        Scanner scanner = new Scanner(System.in);
        log(msg);
        return scanner.nextLine();
    }

}
