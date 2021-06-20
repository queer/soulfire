package gg.amy.soulfire.api.minecraft.sound;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;

import static gg.amy.soulfire.api.YouFuckedUp.unimplemented;

/**
 * @author amy
 * @since 5/30/21.
 */
@Bridge("net.minecraft.sounds.SoundSource")
public interface SoundSource {
    @BridgeField("MASTER")
    static SoundSource master() {
        return unimplemented();
    }

    @BridgeField("MUSIC")
    static SoundSource music() {
        return unimplemented();
    }

    @BridgeField("RECORDS")
    static SoundSource records() {
        return unimplemented();
    }

    @BridgeField("WEATHER")
    static SoundSource weather() {
        return unimplemented();
    }

    @BridgeField("BLOCKS")
    static SoundSource blocks() {
        return unimplemented();
    }

    @BridgeField("HOSTILE")
    static SoundSource hostile() {
        return unimplemented();
    }

    @BridgeField("NEUTRAL")
    static SoundSource neutral() {
        return unimplemented();
    }

    @BridgeField("PLAYERS")
    static SoundSource players() {
        return unimplemented();
    }

    @BridgeField("AMBIENT")
    static SoundSource ambient() {
        return unimplemented();
    }

    @BridgeField("VOICE")
    static SoundSource voice() {
        return unimplemented();
    }
}
