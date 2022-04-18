# java-universal-string-deobfuscator
> OpenJDK 17.0.1
---

### Usage

`java -jar deobf.jar input.jar type`

### Transformer Types

| Type | Description |
| --- | --- |
| 1 | `needs Decrypt ClassName, MethodName, MethodDesc` |
| 2 | `needs Decrypt MethodName, MethodDesc` |
| 3 | `needs Decrypt MethodDesc, MethodAccess` |
---
if you do not know how to get them you can use [Recaf](https://github.com/Col-E/Recaf)


### Libraries
- ObjectWeb ASM
