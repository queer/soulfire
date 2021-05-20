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