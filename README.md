# soulfire

An experimental mod API for Minecraft. You probably don't want to use this.

soulfire does not expose **any** Minecraft code, but wraps it in a higher-level API.

The name is lowercase. DO NOT capitalise it.

## Why?

- I was bored.
- I hated touching Minecraft code directly when writing mods.
- I hate the idea that mods need to be postprocessed to reobfuscate.

If Forge and Fabric expose Minecraft's APIs directly, soulfire is more like Bukkit.

## Philosophy

If you ever have to touch native Minecraft APIs, do bytecode injection, etc., then that's a bug in soulfire and should
be reported. You should never ever ever have to resort to such hacks. I understand that this will likely create a
maintenance nightmare and break literally everything all the time, but I figure that fuck it, if I'm already shooting
for the moon by making my own modding API then I'm gonna do it the way I want to, dammit.

## FAQ

**Q:** Forge / Fabric exists, this is a waste of time.

**A:** I'm doing this for fun. Not a question.

**Q** This approach is fragile and thus a terrible idea!

**A:** Yup. Also not a question.

**Q:** Won't this approach scale poorly?

**A:** Yup! Again, I'm doing this for fun. I don't really expect anyone to use this, I won't be going out shilling for
       people to switch from Forge / Fabric and use this instead, etc. This is just me and my spare time.

**Q:** Does it work?

**A:** Yes! You can see an example mod [here](https://github.com/queer/soulfire/tree/mistress/soulfire-example-mod).
       Mods go in `.minecraft/soulfire/mods`; this directory will be created for you if it doesn't already exist.

**Q:** Why not a root-level mods directory, ie. `.minecraft/mods`?

**A:** It conflicts with Forge, and that's not very nice.