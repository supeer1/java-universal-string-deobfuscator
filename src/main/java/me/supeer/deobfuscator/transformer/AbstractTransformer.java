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

public abstract class AbstractTransformer {

    public Content content;

    public AbstractTransformer(Content content) {
        this.content = content;
    }

    public abstract void transform();

}
