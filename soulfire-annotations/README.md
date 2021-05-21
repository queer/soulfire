# soulfire-annotations

Annotations to help guide the agent with bytecode generation.

## How does it work?

Since we don't have direct access to Minecraft classes in our development environments, soulfire has a concept of
"bridge" classes. Bridge classes are interfaces with
the [`@Bridge`](https://github.com/queer/soulfire/blob/master/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/Bridge.java)
annotation on them; the annotation indicates what Minecraft class it targets. A bridge class then defines methods --
which may be static -- that bridge into Minecraft fields or methods.
A [`@BridgeField`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/BridgeField.java)
is a method providing access to a specific field -- most often seen for bridging `public static final` fields, but any
field can be bridged.
A [`@BridgeMethod'](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/BridgeMethod.java)
is the exact same idea as a bridged field, but for a method.

Bridge classes may additionally be annotated with any combination of:

- [`@Nontransforming`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/Nontransforming.java):
  The interface is non-transforming, ie it applies no changes to the Minecraft class itself, and is only bridging
  methods or fields.
- [`@Retransforming`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/Retransforming.java):
  Indicates that this bridge is retransforming, ie
  that [`Instrumentation#retransformClasses`](https://docs.oracle.com/en/java/javase/16/docs/api/java.instrument/java/lang/instrument/Instrumentation.html#retransformClasses(java.lang.Class...))
  should be called on the target Minecraft class.
- [`@Setter`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/Setter.java):
  This only applies to `@BridgeField` methods, indicating that the method is a setter for the bridged field.
- [`@Interface`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/Interface.java):
  This indicates that the method target is itself an interface method; this affects bytecode generation. On a bridge
  type, this annotation means that the bridged type is itself an interface. This is used in shim generation.
- [`@TransformAfter`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/TransformAfter.java):
  A "nuclear option" / last resort for misbehaving bytecode transformers. Sometimes, classes need to be transformed in a
  specific order, basically just because my bytecode generation is a pile of hacks upon hacks, and it's frankly a
  miracle that it works.

Bridged methods or fields may be annotated
with [`@DumpASM`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/DumpASM.java)
, which is a debugging tool that dumps the bytecode instructions to stdout, so that you can figure out whether the
bytecode generation is broken. It also serves as a useful indicator of whether a particular bridge is even being
generated (see the `@TransformAfter` description for why).

A [`@Shim`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/Shim.java)
class is like a reverse-bridge. Instead of bridging readable methods to obfuscated Minecraft methods, it does the
reverse; its interfaces are recursively scanned to ensure that as many bridge methods as possible are handled. The
[`@Constructor`](https://github.com/queer/soulfire/blob/mistress/soulfire-annotations/src/main/java/gg/amy/soulfire/annotations/Constructor.java)
annotation marks its constructors with the corresponding Minecraft superclass constructor, for codegen purposes. The
`@Shim` annotation indicates the intended superclass, such that constructor synthesis will work.