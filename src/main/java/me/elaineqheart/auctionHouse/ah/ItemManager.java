package me.elaineqheart.auctionHouse.ah;

import me.elaineqheart.auctionHouse.AuctionHouse;
import me.elaineqheart.auctionHouse.GUI.impl.AuctionHouseGUI;
import me.elaineqheart.auctionHouse.GUI.impl.MyAuctionsGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemManager {
    
    // Static item instances
    private static final Map<String, ItemStack> ITEMS = new HashMap<>();
    
    // Item keys for easy access
    private static final String FILLER_ITEM = "filler_item";
    private static final String LOCKED_SLOT = "locked_slot";
    private static final String REFRESH = "refresh";
    private static final String BACK_TO_MAIN_MENU = "back_to_main_menu";
    private static final String BACK_TO_MY_AUCTIONS = "back_to_my_auctions";
    private static final String INFO = "info";
    private static final String MY_AUCTION = "my_auction";
    private static final String EMPTY_PAPER = "empty_paper";
    private static final String CANCEL = "cancel";
    private static final String COLLECT_EXPIRED_ITEM = "collect_expired_item";
    private static final String CANCEL_AUCTION = "cancel_auction";
    private static final String COMMAND_BLOCK_INFO = "command_block_info";
    private static final String ADMIN_CANCEL_AUCTION = "admin_cancel_auction";
    private static final String ADMIN_EXPIRE_AUCTION = "admin_expire_auction";
    private static final String CONFIRM = "confirm";
    
    // Sort items
    private static final String SORT_HIGHEST_PRICE = "sort_highest_price";
    private static final String SORT_LOWEST_PRICE = "sort_lowest_price";
    private static final String SORT_ENDING_SOON = "sort_ending_soon";
    private static final String SORT_ALPHABETICAL = "sort_alphabetical";
    private static final String MY_SORT_ALL_AUCTIONS = "my_sort_all_auctions";
    private static final String MY_SORT_SOLD_ITEMS = "my_sort_sold_items";
    private static final String MY_SORT_EXPIRED_ITEMS = "my_sort_expired_items";
    private static final String MY_SORT_ACTIVE_AUCTIONS = "my_sort_active_auctions";
    
    // Public accessors for backward compatibility
    public static ItemStack fillerItem;
    public static ItemStack lockedSlot;
    public static ItemStack refresh;
    public static ItemStack backToMainMenu;
    public static ItemStack backToMyAuctions;
    public static ItemStack info;
    public static ItemStack myAuction;
    public static ItemStack sortHighestPrice;
    public static ItemStack sortLowestPrice;
    public static ItemStack sortEndingSoon;
    public static ItemStack sortAlphabetical;
    public static ItemStack mySortAllAuctions;
    public static ItemStack mySortSoldItems;
    public static ItemStack mySortExpiredItems;
    public static ItemStack mySortActiveAuctions;
    public static ItemStack emptyPaper;
    public static ItemStack cancel;
    public static ItemStack collectExpiredItem;
    public static ItemStack cancelAuction;
    public static ItemStack commandBlockInfo;
    public static ItemStack adminCancelAuction;
    public static ItemStack adminExpireAuction;
    public static ItemStack confirm;
    
    /**
     * Initialize all items
     */
    public static void init() {
        createBaseItems();
        createNavigationItems();
        createSortItems();
        createActionItems();
        createAdminItems();
        
        // Set public accessors for backward compatibility
        setPublicAccessors();
    }
    
    /**
     * Create basic GUI items
     */
    private static void createBaseItems() {
        ITEMS.put(FILLER_ITEM, createItem(Material.BLACK_STAINED_GLASS_PANE)
            .hideTooltip(true)
            .build());
            
        ITEMS.put(LOCKED_SLOT, createItem(Material.BARRIER)
            .name(ChatColor.RED + "Locked Slot")
            .build());
            
        ITEMS.put(EMPTY_PAPER, createItem(Material.PAPER)
            .name(ChatColor.GRAY + "")
            .persistentData(new NamespacedKey(AuctionHouse.getPlugin(), "AuctionHouseSearch"), 
                          PersistentDataType.BOOLEAN, true)
            .build());
    }
    
    /**
     * Create navigation items
     */
    private static void createNavigationItems() {
        ITEMS.put(REFRESH, createItem(Material.NETHER_STAR)
            .name(ChatColor.GREEN + "Refresh")
            .lore(ChatColor.GRAY + "Click to refresh the menu")
            .build());
            
        ITEMS.put(BACK_TO_MAIN_MENU, createItem(Material.ARROW)
            .name(ChatColor.GREEN + "Main Menu")
            .lore(ChatColor.GRAY + "Click to go back!")
            .build());
            
        ITEMS.put(BACK_TO_MY_AUCTIONS, createItem(Material.ARROW)
            .name(ChatColor.GREEN + "My Auctions")
            .lore(ChatColor.GRAY + "Click to go back!")
            .build());
            
        ITEMS.put(MY_AUCTION, createItem(Material.ENDER_CHEST)
            .name(ChatColor.GREEN + "My Auctions")
            .lore(ChatColor.GRAY + "Click to view your auctions!")
            .build());
    }
    
    /**
     * Create sort items
     */
    private static void createSortItems() {
        String[] sortOptions = {
            ChatColor.GRAY + " Highest Price",
            ChatColor.GRAY + " Lowest Price", 
            ChatColor.GRAY + " Ending soon",
            ChatColor.GRAY + " Alphabetical"
        };
        
        String[] sortFooter = {
            "",
            ChatColor.YELLOW + "Right-Click to go backwards!",
            ChatColor.GREEN + "Click to switch sort!"
        };
        
        // Main auction house sorts
        ITEMS.put(SORT_HIGHEST_PRICE, createSortItem(0, sortOptions, sortFooter));
        ITEMS.put(SORT_LOWEST_PRICE, createSortItem(1, sortOptions, sortFooter));
        ITEMS.put(SORT_ENDING_SOON, createSortItem(2, sortOptions, sortFooter));
        ITEMS.put(SORT_ALPHABETICAL, createSortItem(3, sortOptions, sortFooter));
        
        // My auctions sorts
        String[] mySortOptions = {
            ChatColor.GRAY + " All Auctions",
            ChatColor.GRAY + " Sold Items",
            ChatColor.GRAY + " Expired Items",
            ChatColor.GRAY + " Active Auctions"
        };
        
        ITEMS.put(MY_SORT_ALL_AUCTIONS, createSortItem(0, mySortOptions, sortFooter));
        ITEMS.put(MY_SORT_SOLD_ITEMS, createSortItem(1, mySortOptions, sortFooter));
        ITEMS.put(MY_SORT_EXPIRED_ITEMS, createSortItem(2, mySortOptions, sortFooter));
        ITEMS.put(MY_SORT_ACTIVE_AUCTIONS, createSortItem(3, mySortOptions, sortFooter));
    }
    
    /**
     * Create action items
     */
    private static void createActionItems() {
        ITEMS.put(INFO, createItem(Material.PAPER)
            .name(ChatColor.GREEN + "Info")
            .lore(
                ChatColor.GRAY + "To set up a new auction use",
                ChatColor.GRAY + "/ah sell 1000",
                ChatColor.GRAY + "while holding the item to sell in your hand.",
                ChatColor.GRAY + "the number will be the price of the item"
            )
            .build());
            
        ITEMS.put(CANCEL, createItem(Material.RED_BANNER)
            .name(ChatColor.RED + "Cancel")
            .build());
            
        ITEMS.put(COLLECT_EXPIRED_ITEM, createItem(Material.RED_DYE)
            .name(ChatColor.RED + "Collect Expired Item")
            .lore("", ChatColor.YELLOW + "Click to collect!")
            .build());
            
        ITEMS.put(CANCEL_AUCTION, createItem(Material.RED_CONCRETE)
            .name(ChatColor.RED + "Cancel Auction Item")
            .lore("", ChatColor.YELLOW + "Click to collect!")
            .build());
            
        ITEMS.put(CONFIRM, createItem(Material.GREEN_BANNER)
            .name(ChatColor.GREEN + "Confirm")
            .build());
    }
    
    /**
     * Create admin items
     */
    private static void createAdminItems() {
        ITEMS.put(COMMAND_BLOCK_INFO, createItem(Material.STRUCTURE_BLOCK)
            .name(ChatColor.GREEN + "Info")
            .lore(
                ChatColor.GRAY + "Click on an item to expire or delete it",
                ChatColor.GRAY + "Expired items can be collected again by the player",
                ChatColor.GRAY + "Deleted items will be removed from the auction house",
                ChatColor.GRAY + "and you will get them in your inventory"
            )
            .build());
            
        ITEMS.put(ADMIN_CANCEL_AUCTION, createItem(Material.RED_CONCRETE)
            .name(ChatColor.RED + "Cancel Auction Item")
            .lore("", ChatColor.YELLOW + "The player won't get the item back! You will collect it!")
            .build());
            
        ITEMS.put(ADMIN_EXPIRE_AUCTION, createItem(Material.RED_DYE)
            .name(ChatColor.RED + "Expire Auction Item")
            .lore("", ChatColor.YELLOW + "Click to make it expire!")
            .build());
    }
    
    /**
     * Create a sort item with highlighted option
     */
    private static ItemStack createSortItem(int selectedIndex, String[] options, String[] footer) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        
        for (int i = 0; i < options.length; i++) {
            if (i == selectedIndex) {
                lore.add(ChatColor.AQUA + "►" + options[i].substring(8)); // Remove initial gray color
            } else {
                lore.add(options[i]);
            }
        }
        
        lore.addAll(Arrays.asList(footer));
        
        return createItem(Material.HOPPER)
            .name(ChatColor.GREEN + "Sort")
            .lore(lore)
            .build();
    }
    
    /**
     * Set public accessors for backward compatibility
     */
    private static void setPublicAccessors() {
        fillerItem = ITEMS.get(FILLER_ITEM);
        lockedSlot = ITEMS.get(LOCKED_SLOT);
        refresh = ITEMS.get(REFRESH);
        backToMainMenu = ITEMS.get(BACK_TO_MAIN_MENU);
        backToMyAuctions = ITEMS.get(BACK_TO_MY_AUCTIONS);
        info = ITEMS.get(INFO);
        myAuction = ITEMS.get(MY_AUCTION);
        sortHighestPrice = ITEMS.get(SORT_HIGHEST_PRICE);
        sortLowestPrice = ITEMS.get(SORT_LOWEST_PRICE);
        sortEndingSoon = ITEMS.get(SORT_ENDING_SOON);
        sortAlphabetical = ITEMS.get(SORT_ALPHABETICAL);
        mySortAllAuctions = ITEMS.get(MY_SORT_ALL_AUCTIONS);
        mySortSoldItems = ITEMS.get(MY_SORT_SOLD_ITEMS);
        mySortExpiredItems = ITEMS.get(MY_SORT_EXPIRED_ITEMS);
        mySortActiveAuctions = ITEMS.get(MY_SORT_ACTIVE_AUCTIONS);
        emptyPaper = ITEMS.get(EMPTY_PAPER);
        cancel = ITEMS.get(CANCEL);
        collectExpiredItem = ITEMS.get(COLLECT_EXPIRED_ITEM);
        cancelAuction = ITEMS.get(CANCEL_AUCTION);
        commandBlockInfo = ITEMS.get(COMMAND_BLOCK_INFO);
        adminCancelAuction = ITEMS.get(ADMIN_CANCEL_AUCTION);
        adminExpireAuction = ITEMS.get(ADMIN_EXPIRE_AUCTION);
        confirm = ITEMS.get(CONFIRM);
    }
    
    /**
     * Get sort item based on sort type
     */
    public static ItemStack getSort(AuctionHouseGUI.Sort sort) {
        switch (sort) {
            case LOWEST_PRICE:
                return ITEMS.get(SORT_LOWEST_PRICE);
            case ENDING_SOON:
                return ITEMS.get(SORT_ENDING_SOON);
            case ALPHABETICAL:
                return ITEMS.get(SORT_ALPHABETICAL);
            default:
                return ITEMS.get(SORT_HIGHEST_PRICE);
        }
    }
    
    /**
     * Get my sort item based on sort type
     */
    public static ItemStack getMySort(MyAuctionsGUI.MySort sort) {
        switch (sort) {
            case ALL_AUCTIONS:
                return ITEMS.get(MY_SORT_ALL_AUCTIONS);
            case SOLD_ITEMS:
                return ITEMS.get(MY_SORT_SOLD_ITEMS);
            case EXPIRED_ITEMS:
                return ITEMS.get(MY_SORT_EXPIRED_ITEMS);
            default:
                return ITEMS.get(MY_SORT_ACTIVE_AUCTIONS);
        }
    }
    
    /**
     * Create a dirt item representing a canceled item
     */
    public static ItemStack createDirt() {
        return createItem(Material.DIRT)
            .name(ChatColor.RED + "Canceled Item")
            .build();
    }
    
    /**
     * Create an item display from auction note
     */
    public static ItemStack createItemFromNote(ItemNote note, Player player) {
        ItemStack item = note.getItem().clone();
        List<String> lore = getOrCreateLore(item);
        
        addAuctionInfo(lore, note, player);
        addAuctionStatus(lore, note);
        
        return updateItemMeta(item, meta -> meta.setLore(lore));
    }
    
    /**
     * Create buying item display
     */
    public static ItemStack createBuyingItemDisplay(ItemNote note) {
        ItemStack item = note.getItem().clone();
        List<String> lore = getOrCreateLore(item);
        
        lore.add(ChatColor.DARK_GRAY + "------------------");
        lore.add(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "BUYING ITEM!");
        
        return updateItemMeta(item, meta -> meta.setLore(lore));
    }
    
    /**
     * Create admin expire item display
     */
    public static ItemStack createAdminExpireItem(ItemNote note, String reason) {
        ItemStack item = note.getItem().clone();
        List<String> lore = getOrCreateLore(item);
        
        addBasicAuctionInfo(lore, note);
        lore.add(ChatColor.RED + "Expired by a moderator!");
        lore.add(ChatColor.GRAY + "Reason: " + reason);
        
        return updateItemMeta(item, meta -> meta.setLore(lore));
    }
    
    /**
     * Create admin delete item display
     */
    public static ItemStack createAdminDeleteItem(ItemNote note, String reason) {
        ItemStack item = createDirt();
        List<String> lore = getOrCreateLore(item);
        
        addBasicAuctionInfo(lore, note);
        lore.add(ChatColor.RED + "Deleted by a moderator!");
        lore.add(ChatColor.GRAY + "Reason: " + reason);
        
        return updateItemMeta(item, meta -> meta.setLore(lore));
    }
    
    /**
     * Create turtle scute buy button (sufficient funds)
     */
    public static ItemStack createTurtleScute(double price) {
        return createItem(Material.TURTLE_SCUTE)
            .name(ChatColor.GOLD + "Buy Item right now!")
            .lore(
                "",
                ChatColor.GRAY + "Price: " + ChatColor.GOLD + price,
                "",
                ChatColor.YELLOW + "Click to buy!"
            )
            .build();
    }
    
    /**
     * Create armadillo scute buy button (insufficient funds)
     */
    public static ItemStack createArmadilloScute(double price) {
        return createItem(Material.ARMADILLO_SCUTE)
            .name(ChatColor.GOLD + "Buy Item right now!")
            .lore(
                "",
                ChatColor.GRAY + "Price: " + ChatColor.GOLD + price,
                "",
                ChatColor.RED + "Not enough coins!"
            )
            .build();
    }
    
    /**
     * Create confirm button with price
     */
    public static ItemStack createConfirm(double price) {
        return createItem(Material.GREEN_BANNER)
            .name(ChatColor.GREEN + "Confirm")
            .lore(ChatColor.GRAY + "Cost: " + ChatColor.GOLD + price)
            .build();
    }
    
    /**
     * Create collect sold item button
     */
    public static ItemStack collectSoldItem(double price) {
        return createItem(Material.DIAMOND)
            .name(ChatColor.GREEN + "Collect Sold Item")
            .lore(
                "",
                ChatColor.GRAY + "Value with taxes: " + ChatColor.GOLD + price,
                "",
                ChatColor.YELLOW + "Click to collect!"
            )
            .build();
    }
    
    // Helper methods
    
    /**
     * Add basic auction information to lore
     */
    private static void addBasicAuctionInfo(List<String> lore, ItemNote note) {
        lore.add(ChatColor.DARK_GRAY + "------------------");
        lore.add(ChatColor.GRAY + "Seller: " + note.getPlayerName());
        lore.add(ChatColor.GRAY + "Price: " + ChatColor.GOLD + note.getPrice());
        lore.add("");
    }
    
    /**
     * Add auction information to lore including player check
     */
    private static void addAuctionInfo(List<String> lore, ItemNote note, Player player) {
        addBasicAuctionInfo(lore, note);
        
        if (Objects.equals(Bukkit.getPlayer(note.getPlayerUUID()), player)) {
            lore.add(ChatColor.GREEN + "This is your own item!");
            lore.add("");
        }
    }
    
    /**
     * Add auction status to lore
     */
    private static void addAuctionStatus(List<String> lore, ItemNote note) {
        if (note.isExpired() && !note.isSold()) {
            if (note.getAdminMessage() != null) {
                if (note.getItem().equals(createDirt())) {
                    lore.add(ChatColor.RED + "Deleted by a moderator!");
                } else {
                    lore.add(ChatColor.RED + "Expired by a moderator!");
                }
                lore.add(ChatColor.GRAY + "Reason: " + note.getAdminMessage());
            } else {
                lore.add(ChatColor.RED + "Expired!");
            }
        } else if (note.isSold()) {
            lore.add(ChatColor.GOLD + "Sold!");
            lore.add(ChatColor.GRAY + "Buyer: " + note.getBuyerName());
        } else if (note.isOnWaitingList()) {
            lore.add("Auction starting in: " + note.getTimeLeft(note.timeLeft() - 60 * 60 * 48));
        } else {
            lore.add("Ends in: " + note.getTimeLeft(note.timeLeft()));
        }
    }
    
    /**
     * Get existing lore or create new list
     */
    private static List<String> getOrCreateLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return new ArrayList<>();
        
        List<String> lore = meta.getLore();
        return lore != null ? new ArrayList<>(lore) : new ArrayList<>();
    }
    
    /**
     * Update item meta using a consumer function
     */
    private static ItemStack updateItemMeta(ItemStack item, java.util.function.Consumer<ItemMeta> metaUpdater) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            metaUpdater.accept(meta);
            item.setItemMeta(meta);
        }
        return item;
    }
    
    /**
     * Item builder class for fluent API
     */
    private static ItemBuilder createItem(Material material) {
        return new ItemBuilder(material);
    }
    
    /**
     * Fluent item builder
     */
    private static class ItemBuilder {
        private final ItemStack item;
        private final ItemMeta meta;
        
        public ItemBuilder(Material material) {
            this.item = new ItemStack(material);
            this.meta = Objects.requireNonNull(item.getItemMeta());
        }
        
        public ItemBuilder name(String name) {
            meta.setItemName(name);
            return this;
        }
        
        public ItemBuilder lore(String... lore) {
            meta.setLore(Arrays.asList(lore));
            return this;
        }
        
        public ItemBuilder lore(List<String> lore) {
            meta.setLore(lore);
            return this;
        }
        
        public ItemBuilder hideTooltip(boolean hide) {
            meta.setHideTooltip(hide);
            return this;
        }
        
        public <T, Z> ItemBuilder persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
            meta.getPersistentDataContainer().set(key, type, value);
            return this;
        }
        
        public ItemStack build() {
            item.setItemMeta(meta);
            return item;
        }
    }
}
