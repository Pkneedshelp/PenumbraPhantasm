package destiny.penumbra_phantasm.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerInventoryData {

    private CompoundTag overworldInv = new CompoundTag();
    private CompoundTag cardKingdomInv = new CompoundTag();
    private String lastDimension = "";

    public CompoundTag getOverworldInv() {
        return overworldInv;
    }

    public CompoundTag getCardKingdomInv() {
        return cardKingdomInv;
    }

    public void setOverworldInv(CompoundTag tag) {
        this.overworldInv = tag;
    }

    public void setCardKingdomInv(CompoundTag tag) {
        this.cardKingdomInv = tag;
    }

    public String getLastDimension() {
        return lastDimension;
    }

    public void setLastDimension(String dim) {
        this.lastDimension = dim;
    }

    public void saveNBT(CompoundTag nbt) {
        nbt.put("overworld_inventory", overworldInv);
        nbt.put("card_kingdom_inventory", cardKingdomInv);
        nbt.putString("last_dimension", lastDimension);
    }

    public void loadNBT(CompoundTag nbt) {
        if (nbt.contains("overworld_inventory"))
            overworldInv = nbt.getCompound("overworld_inventory");

        if (nbt.contains("card_kingdom_inventory"))
            cardKingdomInv = nbt.getCompound("card_kingdom_inventory");

        if (nbt.contains("last_dimension"))
            lastDimension = nbt.getString("last_dimension");
    }
}

