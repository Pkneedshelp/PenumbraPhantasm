package destiny.penumbra_phantasm.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class PlayerInventoryData {

    private ListTag overworldInv = new ListTag();
    private ListTag darkWorldInv = new ListTag();
    private String lastDimension = "";

    public ListTag getOverworldInv() {
        return overworldInv;
    }

    public void setOverworldInv(ListTag inv) {
        this.overworldInv = inv;
    }

    public ListTag getDarkWorldInv() {
        return darkWorldInv;
    }

    public void setDarkWorldInv(ListTag inv) {
        this.darkWorldInv = inv;
    }

    public String getLastDimension() {
        return lastDimension;
    }

    public void setLastDimension(String dim) {
        this.lastDimension = dim;
    }

    public void saveNBT(CompoundTag tag) {
        tag.put("OverworldInv", overworldInv);
        tag.put("DarkWorldInv", darkWorldInv);
        tag.putString("LastDimension", lastDimension);
    }

    public void loadNBT(CompoundTag tag) {
        if (tag.contains("OverworldInv")) {
            overworldInv = tag.getList("OverworldInv", 10);
        }
        if (tag.contains("DarkWorldInv")) {
            darkWorldInv = tag.getList("DarkWorldInv", 10);
        }
        lastDimension = tag.getString("LastDimension");
    }
}
