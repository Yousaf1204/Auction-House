package me.elaineqheart.auctionHouse.GUI.impl;

import me.elaineqheart.auctionHouse.GUI.InventoryButton;
import me.elaineqheart.auctionHouse.GUI.InventoryGUI;
import me.elaineqheart.auctionHouse.GUI.other.Sounds;
import me.elaineqheart.auctionHouse.ah.ItemManager;
import me.elaineqheart.auctionHouse.ah.ItemNote;
import me.elaineqheart.auctionHouse.ah.ItemNoteStorageUtil;
import me.elaineqheart.auctionHouse.ah.Messages;
import me.elaineqheart.auctionHouse.vault.VaultHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class ConfirmBuyGUI extends InventoryGUI{

    private final ItemNote note;

    public ConfirmBuyGUI(ItemNote note) {
        super();
        this.note = note;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null,3*9,"Auction House");
    }

    @Override
    public void decorate(Player player) {
        fillOutPlaces(new String[]{
                "# # # # # # # # #",
                "# # . # . # . # #",
                "# # # # # # # # #"
        },fillerItem());
        this.addButton(11, confirm());
        this.addButton(13, buyingItem());
        this.addButton(15, cancel());
        super.decorate(player);
    }

    private void fillOutPlaces(String[] places, InventoryButton fillerItem){
        for(int i = 0; i < places.length; i++){
            for(int j = 0; j < places[i].length(); j+=2){
                if(places[i].charAt(j)=='#') {
                    this.addButton(i*9+j/2, fillerItem);
                }
            }
        }
    }

    private InventoryButton fillerItem(){
        return new InventoryButton()
                .creator(player -> ItemManager.fillerItem)
                .consumer(event -> {});
    }
    private InventoryButton buyingItem(){
        return new InventoryButton()
                .creator(player -> ItemManager.createBuyingItemDisplay(note))
                .consumer(event -> {});
    }
    private InventoryButton confirm(){
        return new InventoryButton()
                .creator(player -> ItemManager.createConfirm(note.getPrice()))
                .consumer(event -> {
                    Player p = (Player) event.getWhoClicked();
                    if(p.getInventory().firstEmpty() == -1) {
                        p.sendMessage(Messages.get("inventory-full"));
                        Sounds.villagerDeny(event);
                        return;
                    }
                    if (note.isSold()) {
                        p.sendMessage(Messages.get("item-already-sold"));
                        Sounds.villagerDeny(event);
                        return;
                    }
                    if (ItemNoteStorageUtil.noteDoesNotExist(note)) {
                        p.sendMessage(Messages.get("item-no-longer-available"));
                        Sounds.villagerDeny(event);
                        return;
                    }
                    Economy eco = VaultHook.getEconomy();
                    double price = note.getPrice();
                    p.closeInventory();
                    if(!note.canAfford(eco.getBalance(p))) {
                        p.sendMessage(Messages.get("insufficient-funds"));
                        Sounds.villagerDeny(event);
                        return;
                    }
                    eco.withdrawPlayer(p, price);
                    Sounds.experience(event);
                    p.getInventory().addItem(note.getItem());
                    note.setSold(true);
                    note.setBuyerName(p.getName());
                    try {
                        ItemNoteStorageUtil.saveNotes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    p.sendMessage(Messages.getFormatted("purchase-successful", "%seller%", note.getPlayerName()));
                });
    }
    private InventoryButton cancel(){
        return new InventoryButton()
                .creator(player -> ItemManager.cancel)
                .consumer(event -> {
                    Sounds.click(event);
                    event.getWhoClicked().closeInventory();
                });
    }

}