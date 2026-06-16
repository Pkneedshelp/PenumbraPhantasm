package destiny.penumbra_phantasm.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class PlayerInventoryData {

    private ListTag overworldInv = new ListTag();
    private ListTag cardKingdomInv = new ListTag();
    private String lastDimension = "";

    public ListTag getOverworldInv() {
        return overworldInv;
    }

    public void setOverworldInv(ListTag inv) {
        this.overworldInv = inv;
    }

    public ListTag getCardKingdomInv() {
        return cardKingdomInv;
    }

    public void setCardKingdomInv(ListTag inv) {
        this.cardKingdomInv = inv;
    }

    public String getLastDimension() {
        return lastDimension;
    }

    public void setLastDimension(String dim) {
        this.lastDimension = dim;
    }

    public void saveNBT(CompoundTag tag) {
        tag.put("OverworldInv", overworldInv);
        tag.put("CardKingdomInv", cardKingdomInv);
        tag.putString("LastDimension", lastDimension);
    }

    public void loadNBT(CompoundTag tag) {
        if (tag.contains("OverworldInv")) {
            overworldInv = tag.getList("OverworldInv", 10); // 10 = Compound
        }
        if (tag.contains("CardKingdomInv")) {
            cardKingdomInv = tag.getList("CardKingdomInv", 10);
        }
        lastDimension = tag.getString("LastDimension");
    }
}
