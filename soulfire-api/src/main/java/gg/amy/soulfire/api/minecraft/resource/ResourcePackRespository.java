package gg.amy.soulfire.api.minecraft.resource;

import gg.amy.soulfire.annotations.Bridge;
import gg.amy.soulfire.annotations.BridgeField;
import gg.amy.soulfire.annotations.BridgeMethod;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author amy
 * @since 5/19/21.
 */
@Bridge("net.minecraft.server.packs.repository.PackRepository")
public interface ResourcePackRespository {
    @BridgeField("available")
    Map<String, ResourcePackInfo> available();

    @BridgeField("selected")
    List<ResourcePackInfo> selected();

    @BridgeMethod("getAvailableIds()")
    Collection<String> availableIds();

    @BridgeMethod("getSelectedIds()")
    Collection<String> selectedIds();
}
