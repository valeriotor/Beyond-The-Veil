package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.GearBenchBlock;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import static com.valeriotor.beyondtheveil.Registration.*;

public class BTVLanguageProvider extends LanguageProvider {

    public BTVLanguageProvider(PackOutput output, String locale) {
        super(output, References.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + References.TAB_NAME, "Beyond The Veil");
        add(BLUE_BRICKS.get(), "Blue Bricks");
        add(DAMP_CANOPY.get(), "Damp Canopy");
        add(DAMP_FILLED_CANOPY.get(), "Damp Filled Canopy");
        add(DAMP_LOG.get(), "Damp Log");
        add(DAMP_WOOD.get(), "Damp Wood");
        add(DAMP_WOOD_FENCE.get(), "Damp Wood Fence");
        add(DAMP_WOOD_STAIRS.get(), "Damp Wooden Stairs");
        add(DARK_SAND.get(), "Dark Sand");
        add(FISH_BARREL.get(), "Fish Barrel");
        add(FUME_SPREADER.get(), "Fume Spreader");
        add(GEAR_BENCH.get(), "Gear Bench");
        add(IDOL.get(), "Idol");
        add(LAMP.get(), "Lamp");
        add(MEMORY_SIEVE.get(), "Memory Sieve");
        add(SLEEP_CHAMBER.get(), "Sleep Chamber");
        add(SLUG_BAIT.get(), "Slug Bait");
        add(WORN_BRICK_STAIRS.get(), "Worn Brick Stairs");
        add(WORN_BRICKS.get(), "Worn Bricks");

        add(ONIRIC_INCENSE.get(), "Oniric Incense");
        add(SLUG_CATCHER.get(), "Slug Catcher");
        add(SLUG.get(), "Slug");
        add(CANOE.get(), "Canoe");
        add(RUM.get(), "Cup of Rum");
        add(WINE.get(), "Cup of Wine");
        add(ALE.get(), "Cup of Ale");
        add(VODKA.get(), "Cup of Vodka");
        add(MEAD.get(), "Cup of Mead");
        add(CUP.get(), "Empty Cup");
        add(FLUTE.get(), "Flute of the Outer Gods");
        add(WOLF_MEDALLION.get(), "Wolf Medallion");
        add(TABLET.get(), "Tablet");
        add(BRONZE_SPHERE.get(), "Bronze Sphere");
        add(REDSTONE_WEED_SEEDS.get(), "Redstone Grass Seeds");
        add(GHOST_WEED_SEEDS.get(), "Ghost Grass Seeds");
        add(VANILLA_WEED_SEEDS.get(), "Vanilla Grass Seeds");
        add(BLACKJACK.get(), "Blackjack");
        add(SPINE.get(), "Spine");
        add(HELD_VILLAGER.get(), "Villager");
        add(HELD_WEEPER.get(), "Weeper");
        add(HELD_FLETUM.get(), "Fletum");
        add(HELD_SHOGGOTH.get(), "Shoggoth");
        add(SURGERY_TOOLS.get(), "Surgery Tools");
        add(BONE_TIARA.get(), "Bone Tiara");
        add(BLEEDING_BELT.get(), "Bleeding Belt");
        add(DREAM_BOTTLE.get(), "Dream Bottle");
        add(SHOGGOTH_MAP.get(), "Shoggoth Map");
        add(BLOOD_COVENANT.get(), "Blood Covenant");
        add(REVELATION_RING.get(), "Ring of Revelation");
        add(AZACNO_CHARM.get(), "Charm of Azacno");
        add(BLOOD_CROWN.get(), "Crown of Thorns");
        add(CORAL_STAFF.get(), "Coral Staff");
        add(SIGIL_ZOMBIE.get(), "Blood Sigil: Zombie");
        add(SIGIL_SKELLIE.get(), "Blood Sigil: Skeleton");
        add(SIGIL_PLAYER.get(), "Blood Sigil: Player");
        add(SIGIL_PATHWAY.get(), "Blood Sigil: Pathway");
        add(SACRIFICIAL_KNIFE.get(), "Sacrificial Knife");
        add(MEMORY_PHIAL.get(), "Memory Phial");
        add(NECRONOMICON.get(), "Al Azif");
        add(GEAR.get(), "Gear");
        add(SURGEON_SUMMONS.get(), "Surgeon Summons");
        add(CRUCIBLE.get(), "Crucible");
        add(BLACK_MIRROR.get(), "Black Mirror");
        add(FLESH_CARBON_TOKEN.get(), "Token of Flesh and Carbon");


        add(GearBenchBlock.GUI_GEAR_BENCH, "Gear Bench");

        add("tooltip.memory_phial.stored", "This phial stores %1$s.");
        add("tooltip.memory_phial.empty", "This phial is empty.");

        add("interact.idol.notyet", "Nothing happens. For some reason, I feel relieved.");
        add("interact.idol.moreslugs", "More slugs must be consumed to commune.");
        add("interact.idol.power", "NEW POWER: Hold Power key ('R' by default) to choose Power, tap it to cast.");
        add("interact.idol.communion", "My skin crawls. The Communion is sealed.");
        add("interact.idol.slugs", "My spine rattles. The voice is inhuman, my will falters.");
        add("interact.idol.fish_quest", "My heart trembles. Terrible things were spoken, the link strengthened.");
        add("interact.idol.drowned", "I shed a tear. New knowledge was granted, an agonizing Truth.");
        add("interact.lacrymatory.full", "This Lacrymatory is already bound.");
        add("interact.lacrymatory.success", "The %1$s is now bound to the Lacrymatory.");
        add("interact.citymapper.loading", "The City Mapper is slowly loading the surrounding environment.");
        add("interact.citymapper.player0", "Someone else is currently using the City Mapper.");
        add("interact.citymapper.player1", "Someone else is still using the City Mapper.");
        add("interact.citymapper.player2", "I still can't use the City Mapper.. because of someone else.");
        add("interact.citymapper.player3", "Some damn fool is still using the City Mapper, preventing me from using it.");
        add("interact.citymapper.player4", "Someone else's still using the Mapper.. we should get rid of them.");
        add("interact.citymapper.player5", "Someone else is currently using the City Mapper.");
        add("interact.heart.toofar", "The other heart is too far away.");
        add("interact.heart.notfound", "something may have happened to the other heart. I should go check.");
        add("interact.heart.stored", "This heart's position is now stored in my coral staff.");
        add("interact.heart.printlink", "This heart is linked to coordinates x: %1$s, y: %2$s, z: %3$s.");
        add("interact.heart.nolink", "This heart is not linked.");
        add("interact.heart.cleared", "This heart's link has been cleared.");
        add("interact.heart.linked", "The heart at coordinates x: %1$s, y: %2$s, z: %3$s is now linked to this one.");
        add("interact.sacrificial_altar.incomplete", "This altar's structure is incomplete.");
        add("interact.blood_well.amount", "%1$s Blood Skeletons and %2$s Blood Zombies are stored in this well. Or, rather, the otherworldly place it is linked to.");
        add("interact.statue.ownerelse", "This Statue is owned by %1$s.");
        add("interact.statue.ownerme", "This Statue is mine.");
        add("interact.statue.noowner", "This Statue is mine to claim.");
        add("interact.statue.ownerboh", "I do not know who this Statue is linked to.");
        add("interact.statue.alreadyme", "This Statue is already mine.");
        add("interact.statue.alreadyelse", "This Statue is bound to someone other than me, and the link of blood cannot be broken.");
        add("interact.statue.bound", "My blood bathes this Statue. It is now bound, our link unbreakable, unbreachable.");
        add("interact.gearbench.dunno", "I do not know how to use this.");
        add("interact.lacrymatory.amount", "This Lacrymatory holds %1$s/4000 millibuckets of Tears.");
        add("interact.arena.mustbedeep", "I must be Deep to begin the duel.");
        add("interact.arena.occupied", "Someone is already dueling in the Arena.");
        add("enter.arche_portal.initiate", "Teleporting to the Overworld in 5 seconds.");
        add("enter.arche_portal.interrupt", "Teleportation interrupted.");
        add("hit.idol.schiz0", "I should not try to break it.");
        add("hit.idol.schiz1", "I shouldn't be doing this.");
        add("hit.idol.schiz2", "I should stop.");
        add("hit.idol.schiz3", "I should really stop.");
        add("hit.idol.schiz4", "Stop.");
        add("hit.idol.schiz5", "Stop it.");
        add("hit.idol.schiz6", "Stop it now.");
        add("hit.idol.schiz7", "You should really stop.");
        add("hit.idol.schiz8", "§5STOP");
        add("hit.idol.schiz9", "You fool.");
        add("hit.idol.schiz10", "You don't know what you're dealing with.");

        add("multiblock.layer", "Layer");
        add("multiblock.blood_well", "Blood Well");
        add("multiblock.sacrifice_altar", "Sacrificial Altar");
        add("multiblock.dream_shrine", "Dream Shrine");

        add("multiblock.dream_shrine.checksout", "This Dream Shrine has been built correctly.");
        add("multiblock.dream_shrine.noshrine", "This is not a Dream Shrine.");
        add("multiblock.sacrifice_altar.checksout", "This Sacrifice Altar has been built correctly.");
        add("multiblock.sacrifice_altar.noaltar", "This is not a Sacrifice Altar.");

        add("shoggoth.hasmapalready0", "This Shoggoth is already building.. it wouldn't be wise to interrupt this thing, would it?");
        add("shoggoth.hasmapalready1", "I should stop trying to command the Shoggoth while it's doing its own thing. It is dangerous.");
        add("shoggoth.hasmapalready2", "Only a fool would try to divert a Shoggoth's attention upon themselves.. and I am no fool, am I??");
        add("shoggoth.hasmapalready3", "Stop.");
        
        add("gui.activebauble.passiveon", "Passive on.");
        add("gui.optionwheel.power.greatdreamer.0", "Summon Deep Ones");
        add("gui.optionwheel.power.greatdreamer.1", "Transform");
        add("gui.optionwheel.power.greatdreamer.3", "Water Walking");

        add("gui.dialogue.talk", "Talk");
        add("gui.dialogue.trade", "Trade");
        //add("gui.sleep_chamber.wake", "Wake Up");

        add("gui.tablet.inscription0", "There is nothing in the Void.<BR>And yet everything comes from There.<BR>That black portal to Infinity.");
        add("gui.tablet.inscription1", "Knowledge is to be feared,<BR>not sought.<BR>It may bring Power,<BR>but at what cost?");
        add("gui.tablet.inscription2", "Infinite Yous throughout infinite moments,<BR>among infinite timelines<BR>within infinite Infinities.<BR>You are nothing. Do not forget that.");
        add("gui.tablet.inscription3", "The deepest Oceans touch the Void.<BR>Water is unsafe, it is Chaos.<BR>Yet from it comes Life.");
        add("gui.tablet.inscription4", "Deaths upon Deaths upon Deaths.<BR>But you are not truly immortal.<BR>You are nothing to Eternity.");
        add("gui.tablet.inscription5", "The Great Dreamer<BR>filters the Veil<BR>into reality.<BR>Dreams go through Him.");
        add("gui.tablet.inscription6", "He is the gnawing sensation<BR>at the back of your mind,<BR>driving you towards unholy acts.<BR>You can't escape.");
        add("gui.tablet.inscription7", "City builders, aeons old.<BR>Even the greatest civilization<BR>is no match to Time.");
        add("gui.tablet.inscription8", "Masses of flesh,<BR>born from unfiltered Chaos.<BR>Among their screeches<BR>you can hear weeping.");
        add("gui.tablet.inscription9", "Fear the Judgement<BR>of Dagon, ArchPriest<BR>of The Great Dreamer.<BR>Do not seek it.");

        add("gui.activebauble.bauble0", "Necklace");
        add("gui.activebauble.bauble1", "Ring 1");
        add("gui.activebauble.bauble2", "Ring 2");
        add("gui.activebauble.bauble3", "Belt");
        add("gui.activebauble.bauble4", "Head");
        add("gui.activebauble.bauble5", "Body");
        add("gui.activebauble.bauble6", "Trinket");
        add("gui.activebauble.help", "Left click to set as current active bauble (only one allowed)./Right click to toggle passive effect./Green: Active Bauble, passive on./Yellow: Active Bauble, passive off./Purple: Non Active Bauble, passive on./Red: Non Active Bauble, passive off.");
        add("gui.activebauble.active", "Currently active.");
        add("gui.activebauble.inactive", "Currently not active.");
        add("gui.activebauble.passiveoff", "Passive off.");


        add("gui.optionwheel.waterycradle.0", "Rip Spine");
        add("gui.optionwheel.waterycradle.1", "Fill with Water");
        add("gui.optionwheel.waterycradle.3", "Rend Heart");

        add("gui.optionwheel.surgeon.0", "Rip Spine");
        add("gui.optionwheel.surgeon.1", "Fill with Water");
        add("gui.optionwheel.surgeon.3", "Rend Heart");

        add("gui.dream_bottle.charges", "Millibuckets: %1$s");

        add("gui.city_mapper.buildingx", "Center X: %1$s");
        add("gui.city_mapper.buildingy", "Center Y: %1$s");
        add("gui.city_mapper.buildingrot", "Facing: %1$s");
        add("gui.city_mapper.buildinglength", "Length: %1$s");
        add("gui.city_mapper.buildingwidth", "Width: %1$s");
        add("gui.city_mapper.buildingsize", "Size: %1$sx%2$s");
        add("gui.city_mapper.rot0", "North");
        add("gui.city_mapper.rot1", "East");
        add("gui.city_mapper.rot2", "South");
        add("gui.city_mapper.rot3", "West");
        add("gui.city_mapper.create", "Create");
        add("gui.city_mapper.save", "Save Changes");
        add("gui.city_mapper.reloadmap", "Reload Map");
        add("gui.city_mapper.incsize", "TIP: It is recommended to increase GUI size for the purpose of this task.");

        add("gui.drowned.youdrowned", "You Drowned");
        add("gui.drowned.greatdreamer", "Believe in the Great Dreamer");
        add("gui.drowned.ancientgods", "Believe in the gods of your ancestors");
        add("gui.drowned.yourself", "Believe in Yourself");
        add("gui.drowned.believe", "Believe");
        add("gui.drowned.know", "Know");
        add("gui.drowned.youare", "YOU ARE");
        add("gui.drowned.nothing", "NOTHING");
        add("gui.drowned.insignificant", "INSIGNIFICANT");
        add("gui.drowned.worthless", "WORTHLESS");
        add("gui.drowned.gnawing", "I am the Gnawing Sensation at the back of your mind");
        add("gui.drowned.nogd", "The Great Dreamer won't listen to you.");
        add("gui.drowned.noac", "The gods of your ancestors do not exist.");

        add("gui.necronomicon", "Al Azif");
        add("gui.necronomicon.bookmarkmade", "Bookmark created");
        add("gui.research_page.complete", "Progress");

        add("keys.dodge", "Dodge");
        add("keys.activebauble", "Use/Manage Baubles");
        add("keys.power", "Use/Select Power");

        add("building.talltower", "Tall Tower");
        add("building.tower", "Tower");
        add("building.largetower", "Large Tower");
        add("building.pyramid", "Pyramid");
        add("building.wall", "Wall");
        add("building.road", "Road");
        add("building.clinic", "Clinic");
        add("building.great_shrine", "Great Shrine");

        add("angry.shoggoth.bad", "A Shoggoth that keeps slaying others will eventually turn upon its master.");

        add("dagon.greeting.0", "IT IS NOT ALLIANCE I SEEK");
        add("dagon.greeting.1", "YOU WILL SERVE ME");
        add("dagon.greeting.2", "GRAND REWARDS AWAIT YOUR SUCCESS");
        add("dagon.greeting.3", "UNSPEAKABLE CONSEQUENCES WILL FOLLOW YOUR FAILURES");
        add("dagon.greeting.4", "YOU ARE MY HERALD");
        add("dagon.greeting.5", "BRING THIS GOLD TO THE DWELLERS");
        add("dagon.greeting.6", "SO THAT THEY MAY KNOW YOU AS MY AMBASSADOR");
        add("dagon.greeting.7", "THEN COMMUNE WITH GOD'S IDOL");
        add("dagon.greeting.8", "I WILL HEAR YOU");
        add("dagon.bringgold", "These three golden blocks must be brought to the Fisherman Dwellers. We should make haste.");

        add("dagon.judgement.0", "YOU WILL JOIN MY RANKS");
        add("dagon.judgement.1", "PROVE YOUR WORTH TO ME");
        add("dagon.judgement.2", "SLAY THREE OF MY ELDER GUARDIANS");
        add("dagon.judgement.3", "THEN COMMUNE WITH GOD'S IDOL");
        add("dagon.slayguardians", "Three Elder Guardians, in their ancient ocean monuments. A trifle, right?");

        add("dagon.seaborn.0", "ALL LIFE IS BORN FROM WATER");
        add("dagon.seaborn.1", "THROUGH BLOOD THEY ARE LINKED TO THE DEEP");
        add("dagon.seaborn.2", "YOUR LINK GROWS EVER STRONGER");
        add("dagon.seaborn.3", "YOU MUST BATHE IN THE LAND OF ENDERMEN");
        add("dagon.seaborn.4", "ENGULFED IN THE VOID AND CLOSEST TO GOD");
        add("dagon.seaborn.5", "THERE YOU WILL DREAM WHILE IN WATER");
        add("dagon.seaborn.6", "AND THE OCEANS WILL BE YOURS");
        add("dagon.endbath", "We must bathe in the End, and there we must Dream.");

        add("dagon.killkeeper.0", "SLAY THE LIGHTHOUSE KEEPER");
        add("dagon.killkeeper.1", "HIS DEATH WON'T MATTER");
        add("dagon.killkeeper.2", "PROVE HIS WORTHLESSNESS");
        add("dagon.killkeeper.3", "AND I WILL GRANT YOU SOME TRUTH");
        add("dagon.lastwords", "A necessary murder. Should I listen to his last words?");

        add("dagon.final.0", "HE WAS IRRELEVANT");
        add("dagon.final.1", "AND SO ARE YOU");
        add("dagon.final.2", "YOUR DEATH WILL COME IN ITS OWN TIME");
        add("dagon.final.3", "SEEK WORTH IN PRAYER");
        add("dagon.final.4", "GOD WILL NOT LISTEN");

        add("dweller.fisherman.dagon", "§5§oThrough Dagon, The Great Dreamer blesses us once more.");

        add("dweller.fisherman.greeting0", "§5§oLeave us alone, outsider.");
        add("dweller.fisherman.greeting1", "§5§oBegone. We don't want you here.");
        add("dweller.fisherman.greeting2", "§5§oOutsiders are not welcome here.");
        add("dweller.fisherman.greeting3", "§5§oPlease, please stop coming after us.");

        add("dweller.trustedfisherman.greeting0", "§5§oHmph.");
        add("dweller.trustedfisherman.greeting1", "§5§oYou did well to join us.");
        add("dweller.trustedfisherman.greeting2", "§5§oBeing a fisherman isn't hard here, the fish just come to us.");
        add("dweller.trustedfisherman.greeting3", "§5§oDon't like the slugs? You'll soon appreciate them.");

        add("dweller.stockpiler.greeting0", "§5§oYou are no friend of ours, outsider. No one is.");
        add("dweller.stockpiler.greeting1", "§5§oWe can't trust you outsiders. No more.");
        add("dweller.stockpiler.greeting2", "§5§oPlease, please leave us BE.");

        add("dweller.trustedstockpiler.greeting0", "§5§oI was mistaken to think ill of you, outsider.");
        add("dweller.trustedstockpiler.greeting1", "§5§oTruly, we deem you a friend now.");
        add("dweller.trustedstockpiler.greeting2", "§5§oFeel free to take anything from the storehouse.");

        add("dweller.drunk.greeting0", "A newcomer, aye? We dun't see many aoutsiders no'adays.");
        add("dweller.drunk.greeting1", "I ain't frum around here, fella. Me pa' an' me ma' was frum this Hamlet, aye, but me, I was bo'n an' rais'd elsewhar.");
        add("dweller.drunk.greeting2", "I dun't like life here. I dun't like life no more.");
        add("dweller.drunk.greeting3", "Hey, buy me a drink, will ye?");
        add("dweller.drunk.greeting4", "None too shabby, that rum -hic- pardon.");
        add("dweller.drunk.greeting5", "Aye, my 'friends' araound here ain't no fun to chat with. They's got.. secrets to hide.");
        add("dweller.drunk.greeting6", "Huh? No, I dunno anythin'.");
        add("dweller.drunk.greeting7", "Hey, wud ye get me 'nother drink, pal?");
        add("dweller.drunk.greeting8", "Hmm, thar's the gud stuff -hic- excuse me.");
        add("dweller.drunk.greeting9", "We may be isolat'd, but we got gold aplenty! We ain't poor! Well, per'haps I am.");
        add("dweller.drunk.greeting10", "Huh, the gold? It comes.. it comes frum below, aye, frum below.");
        add("dweller.drunk.greeting11", "Hey, off'r me 'nother round, bud!");
        add("dweller.drunk.greeting12", "§oAhhh, ain't that tasty -hic- forgive me.");
        add("dweller.drunk.greeting13", "§oWhen me was a y'ung lad, me pa' told me tales o' the sea an' its dwellars. Ye know, thar's things down thar..");
        add("dweller.drunk.greeting14", "§oThings.. horrid, indescribable things..");
        add("dweller.drunk.greeting15", "§oI'm thi'sty. Got some more coin on ye?");
        add("dweller.drunk.greeting16", "§oHmm mhh, hadn't had this good'a drink in years -hic- my bad.");
        add("dweller.drunk.greeting17", "§oAye, horrid things.. and me mates here.. they adore 'em. Trade with 'em. Some e'en mate with 'em.");
        add("dweller.drunk.greeting18", "§oAnd then they dream...");
        add("dweller.drunk.greeting19", "§oHey, I 'now I shudn't be askin', but.. y'know the drill. Fetch me a drink.");
        add("dweller.drunk.greeting20", "§oAh, mebbe I'm havin' too much -hic- apologies.");
        add("dweller.drunk.greeting21", "§oThe.. things, they gav' us fish.. an' gold! But not fur nuthin', nuh huh.");
        add("dweller.drunk.greeting22", "§oI ain't never killed nobody, no, but my mates.. eh.");
        add("dweller.drunk.greeting23", "§oMood's low, dunno why. I say we drown it wit'another drink!");
        add("dweller.drunk.greeting24", "§5§o-hic- I shudn't be sayin' all this -hic-");
        add("dweller.drunk.greeting25", "§5§oBut had we chos'n moar tradit'onal gods, then me ma' and me pa' wudn't have been kill'd, I say.");
        add("dweller.drunk.greeting26", "§5§oKilled by outsiders, sech as ye. Massacred, more like.");
        add("dweller.drunk.greeting27", "§5§o'nother!");
        add("dweller.drunk.greeting28", "§5§oWel, gud luck ye brought upon ye! Our gods, they's real!");
        add("dweller.drunk.greeting29", "§5§oThe things frum the deeps, they protect us!");
        add("dweller.drunk.greeting30", "§5§oThey give us food! An' gold!");
        add("dweller.drunk.greeting31", "§5§oThey AVENGE us!");
        add("dweller.drunk.greeting32", "§5§oAn' if ye dun't like it, then ye either leave, or.. or do as I did..");
        add("dweller.drunk.greeting33", "§5§oYou.. you join us..");
        add("dweller.drunk.greeting34", "I.. enuff with the alc'hol, had enuff.");
        add("dweller.drunk.greeting35", "...");

        add("dweller.bartender.talk0", "{Hmph. }We don't appreciate outsiders coming here, but if you really need to, I won't deny you a single night's rest. There are beds up above.");
        add("dweller.bartender.talk1", "Don't mind the one in the red suit. He's a drunken fool, and spits out lies like no tomorrow.");
        add("dweller.bartendertrustedbar.talk0", "Hey, there are beds up above if you need some rest.");
        add("dweller.bartendertrustedbar.talk1", "Want a drink? Just ask. First one's on the house.");
        add("dweller.bartendertrustedbar.talk1.option0", "Thanks:I'll take it.");
        add("dweller.bartendertrustedbar.talk1.option1", "Not now,:thanks.");
        add("dweller.bartenderrum.talk0", "There you go, finest quality rum you'll find.");
        add("dweller.bartenderenjoy.talk0", "Enjoy your stay.");

        add("dweller.carpenter.talk0", "{Hmm, hmm. }:Greetings.");
        add("dweller.carpenter.talk1", "Got n'thing to say to ye.");
        add("dweller.carpentertrustedcar.talk0", "Wanna buy an'thing?");
        add("dweller.carpentercanoecar.talk0", "Wanna buy an'th[... | :... | :...]| ask.");
        add("dweller.carpentercanoecar.talk0.option0", "How do:you fish?");
        add("dweller.carpentercanoecar.dontfishtalk0", "Me? I don't fish. |:Me mates, they use me canoes.");
        add("dweller.carpentercanoecar.talk0.option1", "What're the:canoes for?");
        add("dweller.carpentercanoecar.canoesfortalk0", "Me fisher mates use 'em to.. fish. In the ocean.");
        add("dweller.carpentereasyjob.talk0", "An easy job theirs. The Ones from the Deep just give 'em fish.");
        add("dweller.carpentereasyjob.talk1", "You buying my stuff or no?");

        add("dweller.lhkeeper.talk0", "Oh. |{A traveller}[... ] :Welcome.");
        add("dweller.lhkeeper.talk1", "I imagine you stumbled upon our Hamlet by chance, in your endless travels.:I can only envy you, stuck as I am,{ watching this lighthouse.");
        add("dweller.lhkeeper.talk2", "I am left to wonder, though, what does an explorer like you think of our humble Hamlet? |Out of[ all] the beauties you must've admired, what impression does it leave upon you?");
        add("dweller.lhkeeper.talk2.option0", "It strikes me:with a peculiar sense:of awe.");
        add("dweller.lhkeeper.talk2.option1", "I do not like it.:There's an atmosphere:of hostility here.");
        add("dweller.lhkeeper.hamletliketalk0", "{Ohh,} such a pleasure to hear it. Tell me then, traveller,:what is it that{ awes} you in such a{ peculiar} way?");
        add("dweller.lhkeeper.hamletliketalk0.option0", "The unique architecture,:how everything fits:upon the water on which it's built.");
        add("dweller.lhkeeper.hamletarchitecturetalk0", "{Ahh, yes.}:We're very proud of what we've built.|| :And rebuilt. |  :And rebuilt over and over again, just for it to be[ burnt down.");
        add("dweller.lhkeeper.hamletarchitecturetalk1", "You have no idea of what I'm referring to, right?:In your pursuit of knowledge of the world, you have ignored that of history.");
        add("dweller.lhkeeper.hamletliketalk0.option1", "The rare items I find here.:The loot.");
        add("dweller.lhkeeper.hamletloottalk0", "Ah, not just a traveller, but a looter!:What items interest you the most?");
        add("dweller.lhkeeper.hamletloottalk0.option0", "The ancient artifacts.");
        add("dweller.lhkeeper.hamletartifactstalk0", "Those artifacts aren't ours. Well, at least they weren't.:They were brought to us by friends, who took them from foes. |:You don't know what I'm referring to, right?");
        add("dweller.lhkeeper.hamletloottalk0.option1", "The slugs.");
        add("dweller.lhkeeper.hamletslugstalk0", "HA! |THE SLUGS! |Well, don't eat those! |:[You might regret it.");
        add("dweller.lhkeeper.hamletslugstalk1", "You can't[ even begin] to imagine how important those slimy creatures are to us.:They are our means of communion, with the God we chose to believe in.");
        add("dweller.lhkeeper.hamletslugstalk2", "Find that strange? Well, you should.:But if that{ \"strangeness\"} fills you with fear and hatred for us and our customs,| then you're no different from your ancestors.");
        add("dweller.lhkeeper.hamletslugstalk3", "You don't know what I'm referring to, right?");
        add("dweller.lhkeeper.hamletdisliketalk0", "That doesn't surprise me,{ outsider}.:What is it you do not like?");
        add("dweller.lhkeeper.hamletdisliketalk0.option0", "The dwellers.:They seem to despise outsiders.");
        add("dweller.lhkeeper.hamletdwellerstalk0", "Trust me,[ outsider.:They have every right to.] :After what your ancestors did, outsiders are not liked here.|  :But you don't know what I'm talking about, right?");
        add("dweller.lhkeeper.hamletdisliketalk0.option1", "It's unsettling.:Especially that bizarre sculpture:in the center.");
        add("dweller.lhkeeper.hamletidoltalk0", "That's the idol of our very own[ God.]| :The deity we chose to believe in, in the shape we saw fit to portray him with.| :I won't criticise you for finding its appearance unsettling.");
        add("dweller.lhkeeper.hamletidoltalk1", "But if that unsettling feeling were to be replaced with fear and hatred for us and our customs,| then you're no different from your ancestors.");
        add("dweller.lhkeeper.hamletidoltalk2", "You don't know what I'm referring to, right?");
        add("dweller.lhkeeperlackknowledge.talk0", "Are you a{ true seeker of knowledge?}:Could you truly be interested in learning more of our past?");
        add("dweller.lhkeeperlackknowledge.talk1", "I doubt it. Your mind has yet to open.");
        add("dweller.lhkeeperhasknowledge.talk0", "I.. I do sense within you a{ thirst..}:A certain lust for learning.| A kind of curiousness, a foolish but{ fascinating one.");
        add("dweller.lhkeeperlecture.talk0", "Do you wish to know more of our history? Of all the{ suffering} we've endured?");
        add("dweller.lhkeeperlecture.talk1", "Do you wish to know more of what your ancestors did to us, in their fear of the[ unknown?");
        add("dweller.lhkeeperlecture.talk1.option0", "Tell me more.:I wish to know.");
        add("dweller.lhkeeperlecture.tellmetalk0", "It is refreshing to see one of you seek this knowledge instead of fleeing it. |:Although, of course.. |I must warn you. |{:Knowledge is fear.");
        add("dweller.lhkeeperlecture.tellmetalk1", "The more you know, |the more you see, |the{ blinder you are,} |once you realise what Veil of illusions is clouding your mind. |:An{ unbreachable] Veil. | Trust me,{ I tried}. To no avail.");
        add("dweller.lhkeeperlecture.talk1.option1", "Keep your lies:to yourself,:fiend.");
        add("dweller.lhkeeperlecture.fiendtalk0", "Why,{ you truly are no different from your ancestors...} are you? |:You just think us monsters, right? Twisted, contorted beings engaging in unholy customs?");
        add("dweller.lhkeeperlecture.fiendtalk1", "And what is it that drives such thoughts, if you don't mind me asking?:Is it our pale, ghastly skin? Or is it that bizarre sculpture in the central square?");
        add("dweller.lhkeeperlecture.fiendtalk1.option0", "It is exactly:those things:you're talking about.:You are inhuman.");
        add("dweller.lhkeeperlecture.inhumantalk0", "That |is true.:But what is it that drives humans to[ slaughter] all that resembles them yet isn't truly like them? | :And{ declare themselves SAINTS in the process?");
        add("dweller.lhkeeperlecture.inhumantalk1", "Ignorance. |Yes, maybe that is it. | :Without knowledge, you make assumptions.[ Dangerous ones.");
        add("dweller.lhkeeperlecture.inhumantalk2", "Why don't you relieve yourself of your prejudice, outsider, and let me tell you how things truly went?");
        add("dweller.lhkeeperlecture.fiendtalk1.option1", "All of you just:dismissed me with the:very same prejudice you:are now accusing me of.");
        add("dweller.lhkeeperlecture.prejudicetalk0", "...truth is, outsider..  we are afraid. |:Terribly afraid of ones like you. Ours isn't prejudice as much as it is wariness, justified suspiciousness.");
        add("dweller.lhkeeperlecture.prejudicetalk1", "If you truly want to fight prejudice, then why don't you listen to me, and let me tell how things truly went?");
        add("dweller.lhkeeperlecture2.talk0", "So, traveller, what'll it be? Will you listen now to our story?");
        add("dweller.lhkeeperlecture2.talk0.option0", "Fine.:Go ahead.");
        add("dweller.lhkeeperlecture2.talk0.option1", "Not now.");
        add("dweller.lhkeeperlecture2.lecturetalk0", "There are many Hamlets like ours in this world, although not as many as there once were. But they've all got something in common.");
        add("dweller.lhkeeperlecture2.lecturetalk1", "You may have been turned off initially by the blackness and dampness of the surrounding territory, but everytime you see it, it means that at one point in time a Hamlet like ours stood there.");
        add("dweller.lhkeeperlecture2.lecturetalk2", "The ground wasn't always like this, though. :Contrary to what some may think, we didn't settle here because of any appeal the soil had upon us. :In fact, we are the very reason the ground became so.");
        add("dweller.lhkeeperlecture2.lecturetalk3", "It was our worship of our Deity that made it happen, and if that doesn't prove its realness, then I don't know what will.:Unfortunately, many of[ your ancestors] did not like the idea of a god truer than theirs.");
        add("dweller.lhkeeperlecture2.lecturetalk4", "In fact they were so disgusted at the idea of a deity turning the ground black and damp that they deemed us 'heretics', and started hunting us down.[ One by one.]");
        add("dweller.lhkeeperlecture2.lecturetalk5", "{Our homes were burned, our people slaughtered.} Our fathers, the survivors, lost everything. It was a{ massacre.}{ It was a[ GENOCIDE].");
        add("dweller.lhkeeperlecture2.lecturetalk6", "And they deemed themselves \"Saints\" and \"Hunters\", those{ merciless murderers.");
        add("dweller.lhkeeperlecture2.lecturetalk7", "But we had friends. Powerful friends, who pulled the war away from us, and upon themselves. Oh, and if it wasn't for them, our whole people would've been{ eradicated.");
        add("dweller.lhkeeperlecture2.lecturetalk8", "Nobody won in those times. And all of it for{ your ancestors'} disdain for that which is different. For the dark sand below us, and the weird shape of our Deity.");
        add("dweller.lhkeeperlecture2.lecturetalk9", "Do you understand now why my fellow dwellers, as well as I, have such disdain for outsiders? Why we are so wary of them?");
        add("dweller.lhkeeperlecture2.lecturetalk9.option0", "Who are these:'friends' you:spoke of?");
        add("dweller.lhkeeperlecture2.lecturetalk9.option1", "I understand.:You have my sympathies.");
        add("dweller.lhkeeperlecture2.friendslecturetalk0", "That is none of your concern, for as long as you remain an outsider.:But that doesn't have to stay like that.");
        add("dweller.lhkeeperlecture2.thankstalk0", "And you have my gratitude. For listening to my tale, our history.:For relieving me of the burden of knowledge.");
        add("dweller.lhkeepergratitude.talk0", "Should you need to know more, ask.");
        add("dweller.lhkeeperdreamer.talk0", "You are a Dreamer. I know that. |:You seek Truth. I know that too.");
        add("dweller.lhkeeperdreamer.talk1", "You wish to breach the Veil. To venture Beyond it. |:Well, you already did, if only in Dream, unable to recall anything.");
        add("dweller.lhkeeperdreamer.talk2", "You now desperately try to convince yourself that you can do it. Succeed where all have failed. Ridding yourself of lies.");
        add("dweller.lhkeeperdreamer.talk2.option0", "Help me.");
        add("dweller.lhkeeperdreamer.talk2.option1", "I AM SICK:OF LIES.");
        add("dweller.lhkeeperdreamer.liestalk0", "We all are, until we realise how much worse the Truth is. ||:I will help you.");
        add("dweller.lhkeepergreatdreamer.talk0", "That statue, in the central square, it represents our marine Deity. |:The Great Dreamer.");
        add("dweller.lhkeepergreatdreamer.talk1", "You seek the Truth, The Great Dreamer hides it. |:To speak with Him, is to go{ Beyond the Veil.");
        add("dweller.lhkeeperocean.talk0", "Traveller, all Dreams go through water. Through The Great Dreamer. |:Look at the Ocean.");
        add("dweller.lhkeepernewyou.talk0", "[Oh my.] |:You communed.");
        add("dweller.lhkeepernewyou.talk1", "The slugs. Eat them. Even if they hurt.");
        add("dweller.lhkeeperimpressed.talk0", "I am impressed traveller. Truly. |:I am impressed by your resolve, your strength of will, your determination in your quest for knowledge, as dangerous as it may be.");
        add("dweller.lhkeeperimpressed.talk1", "I am impressed by your open mind, realising the misdeeds of your ancestors and disavowing them. |:And most of all, I am impressed at how wholly you pledge yourself to your new belief.");
        add("dweller.lhkeeperimpressed.talk2", "Be wary of disappointment. Sooner or later, it'll come. It'll sting.");
        add("dweller.lhkeepercanoe.talk0", "For now, get on a canoe and sail. Talk to a carpenter.");
        add("dweller.lhkeeperritualintro.talk0", "But now, it's time you take the next step.");
        add("dweller.lhkeeperritual.talk0", "We Dwellers can't breathe water from the moment we're born. No, it comes later.");
        add("dweller.lhkeeperritual.talk1", "It is only through endless devotion to The Great Dreamer that an outsider such as you may be granted this Gift. :To us it's different. Genetic. I won't mention the details.");
        add("dweller.lhkeeperritual.talk2", "But ultimately, for both of our kinds it is only through an act of faith that we may be granted the Gift. |:You must drown.");
        add("dweller.lhkeeperritual.talk3", "There is a ritual that must be made. I could explain it to you, yet I am certain that that voice in your head will take care of it.");
        add("dweller.lhkeeperritual.talk4", "I still remember when I underwent the ritual. You will too.");
        add("dweller.lhkeeperritual.talk5", "I was under the water, I couldn't breathe, I was going to die, I knew I was and I regretted it, I regretted everything and I couldn't breathe and they had lied to me and they had left me to drown and I had to believe but I couldn't and I was dying and I BREATHE. :In. And out. In. And out. Again. And again.");
        add("dweller.lhkeeperritual.talk6", "That was my Baptism. Soon, you shall have yours.");
        add("dweller.lhkeeperfriend.talk0", "Good luck, friend.");
        add("dweller.lhkeeperiknow.talk0", "Ah, I Dreamt of you tonight. You wish to tell me something.");
        add("dweller.lhkeeperiknow.talk0.option0", "The drunken one:told me:everything.");
        add("dweller.lhkeeperiknow.talk0.option1", "Have you:always been:victims?");
        add("dweller.lhkeeperiknow.drunktalk0", "And you would trust the words of an old, insane drunken fool? |:Why, of course you should. You'll find that insanity brings one closer to Truth, for better or worse.");
        add("dweller.lhkeeperiknow.victimstalk0", "The drunken one appeared in my Dream as well. :It was him who told you something, wasn't he?");
        add("dweller.lhkeeperoldtruth.talk0", "From his words, an old Truth arises. :MY ancestors' traditions went too far at times. Sacrifices, murders, experiments.");
        add("dweller.lhkeeperoldtruth.talk1", "Blood lays in the black sand beneath us. They did it for power, through unholy pacts. The Great Dreamer, or rather, his ArchPriest, demanded payment.");
        add("dweller.lhkeeperoldtruth.talk2", "You didn't need this Truth. It would've only dissuaded you from your goal. :You see? I had to hide it.  My own Veil, of sorts.");
        add("dweller.lhkeeperoldtruth.talk3", "That, however, does not excuse in the slightest that which happened to us. :No sin is great enough to justify GENOCIDE.");
        add("dweller.lhkeeperoldtruth.talk3.option0", "You did wrong.:Punishment was:due.");
        add("dweller.lhkeeperoldtruth.talk3.option1", "Your actions did:not deserve such:punishment.");
        add("dweller.lhkeeperoldtruth.genocidedisagreetalk0", "There is no right answer. :But know that I{ despise} those who think the way you do.");
        add("dweller.lhkeeperoldtruth.genocideagreetalk0", "That is what we think as well. :Yet your forefathers knew better, it seems.");
        add("dweller.lhkeeperpast.talk0", "It matters not. Let the past stay past, we are not the ones who may walk through time. Only speak.");
        add("dweller.lhkeeperend.talk0", "Granting me a few last words? |Thank you, traveller. :I'll make it quick.");
        add("dweller.lhkeeperend.talk1", "Let's start from an objective Truth. A mathematical one. :The greatest number you could ever think of is nothing to infinity.");
        add("dweller.lhkeeperend.talk2", "Another Truth. I am insignificant. :There are countless others exactly like me in this very world. Exact copies. :I am just a bunch of ifs and fors, so irrelevant that you would mete out my death with barely any hesitation.");
        add("dweller.lhkeeperend.talk3", "My question, outsider, is this. :Do you really claim a mind of infinite complexity? I am sorry to say but, that's not the way it is. Billions upon billions of synapses, yet, sooner or later, even your mind could be copied. Just like mine.");
        add("dweller.lhkeeperend.talk4", "That is a snippet of Truth, outsider, just for you. Your life is worth naught.");
        add("dweller.lhkeeperend.talk5", "It does not matter what world you come from, the Truth spans all realms. | :Because it lies Beyond The Veil, in the region between all universes.");
        add("dweller.lhkeeperend2.talk0", "Go ahead, strike me down. My death won't matter. || :And neither will yours.");

        add("dweller.scholar.talk0", "Oh my! A traveller! |:Hello! Hi! Greetings! Welcome! Salutations!");
        add("dweller.scholar.talk1", "I am this Hamlet's Scholar! A rare profession nowadays. :Haha. Most of us are dead.");
        add("dweller.scholar.talk2", "No worries though. Thou art a friend, ain't ya? |:Feel free to browse the Town Hall.");
        add("dweller.scholarseeya.talk0", "See thee later, dude.");
        add("dweller.scholarweeper.talk0", "Oi, art thou dabblin' in the art of Weeper making? |:Nice.");
        add("dweller.scholarweeper.talk1", "Ya know, back in the day we used to make Weepers too. :Then we stopped, since some people didn't like that practice of ours, and in the end we had enough to weep of our own!");
        add("dweller.scholarweeper.talk2", "Yeah, things were tough back then. :My friends named me Weeper Ad Honorem after me pa and me ma were.. ya know..");
        add("dweller.scholarweeper.talk3", "We still weep at times, absorbed in recollection. :So please, friend, hearken our chants. :And weep with us.");
        add("dweller.scholarseeya2.talk0", "See thee later, dude.");
        add("dweller.scholarshoggoth.talk0", "Oi, dost thou knoweth what a SHOGGOTH is?");
        add("dweller.scholarshoggoth.talk0.option0", "Yes, I do.");
        add("dweller.scholarshoggoth.talk0.option1", "Enlighten me.");
        add("dweller.scholarshoggoth.sorrytalk0", "Oh. |I'm sorry, really. |:That is not a knowledge borne lightly.");
        add("dweller.scholarshoggoth.sorrytalk1", "I beg of thee, friend. :Don't trust the Shoggoth. Thou will regret it.");
        add("dweller.scholarshoggoth.sorrytalk2", "It was that voice in thy head that told ya how to make one, eh?");
        add("dweller.scholarshoggoth.sorrytalk2.option0", "What voice?");
        add("dweller.scholarshoggoth.sorrytalk2.option1", "Yes.");
        add("dweller.scholarshoggoth.enlightentalk0", "Oh no I'm not doin' that. :Thou art lucky enough as it is, my dude.");
        add("dweller.scholarshoggoth.enlightentalk1", "Yet I fear that that voice in thy head will, sooner or later, tell thee how to make one.");
        add("dweller.scholarshoggoth.enlightentalk1.option0", "What voice?");
        add("dweller.scholarshoggoth.enlightentalk1.option1", "Tell me more.");
        add("dweller.scholarbreath.talk0", "Remember when ya were baptised and then breathed HARD in a voice that wasn't thine? :Heh.");
        add("dweller.scholarseeya3.talk0", "See thee later, dude.");

        add("weeper.dialogue.0", "§5§oWhen I gaze at the horrors before me, all I have left is to weep.");
        add("weeper.dialogue.1", "§5§oYou are lucky, master, in your ignorance of what lies Beyond.");
        add("weeper.dialogue.2", "§5§oMaster, the water you put in my brain.. it's agonizing, yet the pain is not of physical nature.");
        add("weeper.dialogue.3", "§5§oEverything is so meaningless.. you'll see, once you dip your head in water.");
        add("weeper.dialogue.4", "§5§oMaster.. I beg of you.. rip my spine. Make me whole.");
        add("weeper.dialogue.5", "§5§oI cannot *see* the world around me, yet I *know* it, even better than you do, Master. And it is most horrid.");
        add("weeper.dialogue.6", "§5§oYou baptized me Master.. you are holy, a god among worms.");
        add("weeper.dialogue.7", "§5§oI hear whispers.. please.. somebody.. stop them...");
        add("weeper.dialogue.8", "§5§oI Dreamt of you. You were laughing, but not of joy. Your eyes were hollow.");
        add("fletum.dialogue.0", "§5§oThe world is beautiful. Like an Ocean of tears.");
        add("fletum.dialogue.1", "§5§oSplish splash. Heehee.");
        add("fletum.dialogue.2", "§5§oI am whole.");
        add("fletum.dialogue.3", "§5§oI see beautiful shapes.. transparent and fluid, like a watery surface.");
        add("fletum.dialogue.4", "§5§oI see a pocket of red at the bottom of the Ocean, like a ruby within a diamond.");
        add("fletum.dialogue.5", "§5§oI Dreamt of a beautiful rainbow. It was red.");
        add("fletum.dialogue.6", "§5§oMy Dreams colour the world! I am sure, because your face is red, heehee.");
        add("fletum.dialogue.7", "§5§oThe Crawling Chaos whispered to me. He said I'm his favourite, heehee!");
        add("weeper.shoggoth.call", "§5§oMaster! Master! I have a secret that I wish to share!");
        add("weeper.shoggoth.0", "§5§oMaster.. I can share a terrible, terrible secret with you.");
        add("weeper.shoggoth.1", "§5§oAll I ask in return is for you to promise to grant me a wish, a single wish. Speak to me again if you accept.");
        add("weeper.shoggoth.2", "§5§oOh.. Master.. my dear Master. Thank you. The secret has been kept for too long.");
        add("weeper.shoggoth.3", "§5§oMaster, my Master. Do you know what a SHOGGOTH is?");
        add("weeper.shoggoth.4", "§5§oMost died long ago, yet still a few roam the deepest seas. But you can make one, too.");
        add("weeper.shoggoth.5", "§5§oThat little voice in your head, the one that drives your writing hand.. it'll teach you how to make one.");
        add("weeper.shoggoth.6", "§5§oMy wish, Master. It is time for my wish. I beg you, my dear, dear Master.. please..");
        add("weeper.shoggoth.kill", "§5§o..kill me.");
        add("weeper.shoggoth.please", "§5§oPlease, I beg of you, kill me.");
        add("weeper.shoggoth.please2", "§5§oPLEASE MASTER, KILL ME.");
        add("weeper.shoggoth.promised", "§5§oMASTER, YOU PROMISED.");

        add("mirror.shivers", "My black mirror is shivering.");
        add("mirror.stopshivers", "My black mirror stopped shivering.");
        add("mirror.endbutton", "(End)");
        add("mirror.continuebutton", "(Continue)");        
        add("mirror.nodialogue.first_node.anyone_there", "Is anyone there?");
        add("mirror.nodialogue.anyone_there.0", "[Y]There is no answer.");     
        add("mirror.start.first_node.anyone_there", "Is anyone there?");
        add("mirror.start.anyone_there.0", "[Y]There is no answer.");
        add("mirror.start.second_node.anyone_there2", "IS ANYONE THERE?");
        add("mirror.start.anyone_there2.0", "[Y]There is still no answer. You should stop enquiring.");
        add("mirror.start.whosyou.whosyou", "'You'? WHAT DO YOU MEAN 'YOU'?");
        add("mirror.start.whosyou.0", "[Y]...");
        add("mirror.start.whosyou.1", "Someone's hiding.. here.. inside of me?");
        add("mirror.start.whoshiding.whoareyou", "Who are you? What are you doing.. in my mind?");
        add("mirror.start.whoareyou.0", "[Y]You wouldn't understand.");
        add("mirror.start.whoshiding.getout", "Get out. Get out get out get out GET OUT OF ME.");
        add("mirror.start.getout.0", "[Y]You do not understand what you're asking for.");
        add("mirror.start.getout.1", "[Y]I gave you all this.");
        add("mirror.start.gavewhat.gavewhat", "Gave me what?");
        add("mirror.start.gavewhat.0", "[Y]Everything. I've been your guiding hand.");
        add("mirror.start.gavewhat.name", "What is your name?");
        add("mirror.start.understand.name", "What is your name?");
        add("mirror.start.name.0", "[Y]You can't pronounce it.");
        add("mirror.start.pronounce.parasite", "Are you a parasite?");
        add("mirror.start.parasite.0", "[Y]Our relationship is mutually beneficial.");
        add("mirror.start.parasite.1", "[Y]I granted you knowledge, you granted me a vessel.");
        add("mirror.start.vessel.novessel", "Seek another vessel then.");
        add("mirror.start.vessel.more", "Then grant me more knowledge.");
        add("mirror.start.vessel.thankful", "If it was through you that I reached The Great Dreamer, then I am thankful.");
        add("mirror.start.novessel.0", "[Y]Our bond goes too deep for either of us to sunder it.");
        add("mirror.start.novessel.1", "[Y]It is far more ancient than either of us.");
        add("mirror.start.novessel.2", "[Y]Too much to explain right now. Soon, I'll give you answers. Truth.");
        add("mirror.start.truth.youknow", "You know the Truth?");
        add("mirror.start.youknow.0", "[Y]A little more than you do.");
        add("mirror.start.youknow.1", "[Y]But we can learn more of it, together, if you follow me.");
        add("mirror.start.more.0", "[Y]It is no easy task, but I shall.");
        add("mirror.start.more.1", "[Y]It is necessary, for us to go forward.");
        add("mirror.start.forward.whereto", "Where to?");
        add("mirror.start.whereto.0", "[Y]To The Great Dreamer.");
        add("mirror.start.reached1.already", "But I reached Him already?!");
        add("mirror.start.already.0", "[Y]You have not.");
        add("mirror.start.thankful.0", "[Y]Yes, through me it was that you communed.");
        add("mirror.start.thankful.1", "[Y]Yet we never truly reached Him.");
        add("mirror.start.reached2.wat", "What? I spoke wih Him! I heard Him and He heard me!");
        add("mirror.start.reached2.insignificant", "Then.. then I truly am insignificant.. irrelevant..");
        add("mirror.start.wat.0", "[Y]Only through Dagon were your thoughts connected. A Veil before the Veil.");
        add("mirror.start.wat.1", "Lies beneath lies..");
        add("mirror.start.wat.2", "[Y]Indeed.");
        add("mirror.start.insignificant.0", "[Y]Several times you have reached this conclusion, but it hasn't yet stuck, has it?");
        add("mirror.start.insignificant.1", "[Y]You're taking it harder, or maybe easier, than I did, when I was your age.");
        add("mirror.start.age.old", "Wait.. how old are you?");
        add("mirror.start.old.0", "[Y]In time, you'll look back at this question, and laugh at it.");
        add("mirror.start.nearlythere.howdreamer", "How will we reach The Dreamer?");
        add("mirror.start.howdreamer.0", "[Y]Through Arche. The deepest Ocean.");
        add("mirror.start.howdreamer.1", "[Y]Beneath Arche there is the Void. And then, the Veil.");
        add("mirror.start.howdreamer.2", "[Y]The Great Dreamer Himself.");
        add("mirror.start.dreamer.veil", "What exactly *is* the Veil?");
        add("mirror.start.veil.0", "[Y]The filter through which what you know as 'reality' is born.");
        add("mirror.start.veil.1", "[Y]The curtain that hides all that is True.");
        add("mirror.start.veil.2", "[Y]The gateway that only Dreams may go through.");
        add("mirror.start.guide.guiding", "How will you take me there?");
        add("mirror.start.guiding.0", "[Y]I've been your guiding hand, the one that helped you write.");
        add("mirror.start.guiding.1", "[Y]Our researches are born out of both of our minds. I'll help you through Al Azif.");
        add("mirror.start.final.whoareyou2", "I still wish to know who you are.");
        add("mirror.start.final.letsgetgoing", "Let's get going then. (End)");
        add("mirror.start.whoareyou2.0", "[Y]A friend. One who had to conceal his own identity for your sake.");
        add("mirror.start.whoareyou2.1", "[Y]Cover's blown. We ride together now.");
        add("mirror.start.final2.letsgetgoing2", "Let's get going then. (End)");       
        add("mirror.afterstart.first.what", "What should I do?");
        add("mirror.afterstart.what.0", "[Y]Open Al Azif. I'll guide you.");       
        add("mirror.beforearche.first.everbeen", "Have you ever been to Arche yourself?");
        add("mirror.beforearche.everbeen.0", "[Y]Yes, long ago. Though it has changed little since then.");
        add("mirror.beforearche.changedlittle.howdoyouknow", "How do you know that?");
        add("mirror.beforearche.howdoyouknow.0", "[Y]I've seen it again, since then.");
        add("mirror.beforearche.seenagain.recording", "As in a recording?");
        add("mirror.beforearche.recording.0", "[Y]More like a livestream, though still not quite.");
        add("mirror.beforearche.recording.1", "[Y]It's not something I am willing to explain just yet. Some truths must be spoonfed.");
        add("mirror.beforearche.spoonfed.frodo", "Alright, keep your secrets. (End)");
        add("mirror.beforearche.spoonfed.trust", "You realise secrets undermine trust?");
        add("mirror.beforearche.trust.0", "[Y]Yes. Which is why I will tell you. When the time is ripe.");     
        add("mirror.archepreparation.first.here", "I'm here.");
        add("mirror.archepreparation.here.0", "[Y]Bring a few torches, Al Azif, this Mirror, and preferably little else. Maybe some normal food, in case you find air pockets to transform into a human again.");
        add("mirror.archepreparation.second.veil", "How do we reach the Veil from Arche?");
        add("mirror.archepreparation.veil.0", "[Y]That, I do not know yet. I never got that far myself.");
        add("mirror.archepreparation.veil.1", "[Y]Perhaps it will reveal itself when we're ready for it."); 
        add("mirror.archepreparation.veil.2", "[Y]Or perhaps when it is ready for us.");
        add("mirror.archepreparation.veil.3", "[Y]For now, focus on getting familiar with Arche. Its food chain, its inhabitants, its challenges.");
        add("mirror.archepreparation.second.arena", "The duels with the Deep Ones, how do I start them?");
        add("mirror.archepreparation.arena.0", "[Y]There should be an appropriate mechanism in the large arena, I believe. We'll see when we're there.");
        add("mirror.archepreparation.second.ictya", "Will the Ictyas attack me on sight?");
        add("mirror.archepreparation.ictya.0", "[Y]Unlikely. In the Deep One form you'd count as a medium sized Ictya, so only ones that are larger and somewhat hungry will attack you.");
        add("mirror.archepreparation.ictya.1", "[Y]When available, they'll generally go for smaller prey. Of course, most will retaliate when attacked.");
        add("mirror.archepreparation.second.ready", "I'm ready.");
        add("mirror.archepreparation.ready.0", "[Y]Then grab the token and enter the Blood Well.");        
        add("mirror.abouttoenter.first.forgot", "How do I enter Arche?");
        add("mirror.abouttoenter.forgot.0", "[Y]Grab the token and enter the Blood Well.");
        add("mirror.abouttoenter.first.end", "(End)");     
        add("mirror.bloodhome.first.what", "What is this place?");
        add("mirror.bloodhome.what.0", "[Y]Your very own Blood Home.");
        add("mirror.bloodhome.what.1", "[Y]There are several such structures in Arche - different visitors to Arche will generally enter different Blood Homes.");
        add("mirror.bloodhome.what.2", "[Y]Every time you enter this underworld you'll be transported to this structure. Feel free to make it your base.");        
        add("mirror.archewater.first.home", "What was that red, blood structure I entered Arche through?");
        add("mirror.archewater.home.0", "[Y]Your very own Blood Home.");
        add("mirror.archewater.home.1", "[Y]There are several such structures in Arche - different visitors to Arche will generally enter different Blood Homes.");
        add("mirror.archewater.home.2", "[Y]Every time you enter this underworld you'll be transported to this structure. Feel free to make it your base.");
        add("mirror.archewater.first.love", "I love Arche.");
        add("mirror.archewater.first.hate", "I hate Arche.");
        add("mirror.archewater.love.0", "[Y]And what do you love most about it?");
        add("mirror.archewater.hate.0", "[Y]How come?");
        add("mirror.archewater.loves.immense", "It is immense - new emotions spring forth, of freedom and wonder.");
        add("mirror.archewater.loves.ictya", "The fauna. Shapes and sizes undreamt of before. Alien, almost.");
        add("mirror.archewater.loves.cities", "The cities and architecture of the Deep Ones - a hidden civilization at the very bottom of the world.");
        add("mirror.archewater.loves.foodchain", "The food chain itself. A tangible lesson of the crude truth that is survival of the fittest.");
        add("mirror.archewater.loves.notreally", "Actually, I don't really love it.");
        add("mirror.archewater.hates.immense2", "It is immense - I feel tiny. No, less than that.");
        add("mirror.archewater.hates.ictya", "The fauna. I feel vulnerable among these new threats, in their bizarre, almost alien shapes and sizes.");
        add("mirror.archewater.hates.octid", "THAT PURPLE INK.");
        add("mirror.archewater.hates.food", "The constant search for food, the growing hunger assailing my miserable belly.");
        add("mirror.archewater.hates.notreally", "Actually, I don't really hate it.");
        add("mirror.archewater.notreally.0", "[Y]Fickle.");
        add("mirror.archewater.immense.0", "[Y]Indeed. So close to the Veil and the Truth lying Beyond it, Arche is truly magnificent.");
        add("mirror.archewater.immense.1", "[Y]An unparalleled feeling of wonder, though perhaps a bitter one, reminding us of how tiny we are.");
        add("mirror.archewater.immense2.0", "[Y]Indeed. So close to the Veil and the Truth lying Beyond it, Arche is a reminder of our inconsequentiality.");
        add("mirror.archewater.immense2.1", "[Y]And yet, I find there to be beauty in it too - a sense of wonder, its massive scale dwarfing and outlasting anything we could construct.");
        add("mirror.archewater.ictya.0", "[Y]The chaotic, ever churning waters of Arche may well give life to all sorts of bizarre creatures.");
        add("mirror.archewater.ictya.1", "[Y]And alien, yes. Perhaps more than one'd think. Other worlds harbour quite similar beings in their own deepest oceans.");
        add("mirror.archewater.alien.oceans", "Do other worlds have oceans of their own?");
        add("mirror.archewater.alien.life", "There's life on other planets?");
        add("mirror.archewater.alien.knowalien", "How do you know all this?");
        add("mirror.archewater.alien.more", "There are other things I wish to speak about.");
        add("mirror.archewater.more.0", "[Y]Go on.");
        add("mirror.archewater.oceans.0", "[Y]Indeed. Arche exists beneath many worlds, and even in the emptiest regions of the universe you can find its waters swirling unendingly.");
        add("mirror.archewater.life.0", "[Y]You did not truly believe to be alone in the universe, did you?");
        add("mirror.archewater.life.1", "[Y]Yes, civilizations exist on another worlds, spread out among the stars. Some have even visited this world.");
        add("mirror.archewater.visited.which", "Which ones?");
        add("mirror.archewater.visited.results", "What did they do?");
        add("mirror.archewater.which.0", "[Y]Their names would be meaningless to you now. Some had the appearance of fungi, others of ticks, others yet had.. well.. they become hard to describe.");
        add("mirror.archewater.which.1", "[Y]Many of them had a penchant for brains, though.");
        add("mirror.archewater.results.0", "[Y]Not much. Many of them had a penchant for brains, though, and some villagers are occasionally abducted.");
        add("mirror.archewater.results.1", "[Y]Don't be scared. After all, it is no more terrifying than our own organ harvesting.");
        add("mirror.archewater.knowalien.0", "[Y]Once again, I must bar you from learning more of my nature. It is for your own good.");
        add("mirror.archewater.knowalien.1", "[Y]Suffice to say - I've met many beings from different worlds. *Many*.");
        add("mirror.archewater.foodchain.0", "[Y]Given the chaotic nature of Arche, it is truly quite marvelous that a hierarchy of order - that is, the food chain - has established itself here.");
        add("mirror.archewater.foodchain.1", "[Y]Of course, our goal is to break that order. To ascend the chain, until we sit at its very top.");
        add("mirror.archewater.cities.0", "[Y]Their buildings are indeed quite striking - so much so that even your ancestors were initially shocked when they first set gaze upon them.");
        add("mirror.archewater.cities.1", "[Y]The Deep Ones they'd once believed to be mindless creatures had in truth known enlightenment far before they did.");
        add("mirror.archewater.ancestors.here", "My ancestors reached Arche?");
        add("mirror.archewater.here.0", "[Y]Some did, yes. Chief among them Saint Jerome, though I do not know what fate he met.");
        add("mirror.archewater.here.1", "[Y]Probably not different from his companions. Crushed beneath the pressure of Arche, the few survivors immediately murdered by Ictya and Deep Ones.");
        add("mirror.archewater.food.0", "[Y]Progress through Arche should be slow and calculated. Do not hesitate to stop to hunt every so often.");
        add("mirror.archewater.food.1", "[Y]A larger Ictya results in a more sizeable repast - at the cost of augmented risk.");
        add("mirror.archewater.octid.0", "[Y]Ohh, do I understand. We'll seek a solution together.");
        add("mirror.archewater.octid.1", "[Y]Perhaps the Deep Ones have found a defense against it. We could search one in their cities.");
        add("mirror.archewater.first.whatis", "Now that I've been to Arche, could you explain to me what it actually is?");
        add("mirror.archewater.whatis.0", "[Y]Arche is the deepest of all oceans, yet, even more than that, it is a *concept*.");
        add("mirror.archewater.whatis.1", "[Y]Arche is the principle of all that belongs to our world. It is that from which all is born, living and not.");
        add("mirror.archewater.whatis.2", "[Y]Flesh and stone, lightning and bone, the Cosmos and the sea, the Dwellers and thee. All from its waters.");
        add("mirror.archewater.whatis.3", "[Y]Arche has been the subject of study of the occult academia for aeons, now. First, by philosophers; then, by warlocks; now, even by scientists.");
        add("mirror.archewater.whatis.4", "[Y]However, there is a flaw in all of this. Can you spot it?");
        add("mirror.archewater.flaw.fire", "Water may not give birth to fire, its opposite.");
        add("mirror.archewater.flaw.principle", "Water may not give birth to itself, even Arche needs a principle.");
        add("mirror.archewater.flaw.nether", "Below the world lies the Nether, then only void above and beneath it.");
        add("mirror.archewater.flaw.academic", "Aeons of academic study, yet no one has ever heard of it?");
        add("mirror.archewater.flaw.none", "I see no flaws.");
        add("mirror.archewater.fire.0", "[Y]Brilliant answer! That is precisely what the philosophers of yore realised.");
        add("mirror.archewater.fire.1", "[Y]So, if fire wasn't born from water - or at least not directly - then there must have been something else, before water, right?");
        add("mirror.archewater.principle.0", "[Y]Great answer! Not the one I was thinking of, mind you, but you reached the same conclusion as the philosophers of yore.");
        add("mirror.archewater.principle.1", "[Y]What they had noticed was that fire may not have been born from water, for they are opposites on the elemental spectrum.");
        add("mirror.archewater.principle.2", "[Y]So, if fire wasn't born from water - or at least not directly - then, just like you said, there must have been something else before water, right?");
        add("mirror.archewater.nether.0", "[Y]Ahh, but Arche lies even below the Nether. Although, the answer does in fact relate to that hellish place.");
        add("mirror.archewater.nether.1", "[Y]Want to try again?");
        add("mirror.archewater.academic.0", "[Y]It is only academics of the occult who studied it, I am afraid.");
        add("mirror.archewater.academic.1", "[Y]In your civilization, a principle of things different from the one posited by religious leaders was considered heretic, as a late friend of yours may have already mentioned.");
        add("mirror.archewater.academic.2", "[Y]Care to try again?");
        add("mirror.archewater.none.0", "[Y]Nor did the philosophers, at first. But you have seen more Truth than they did, and can attempt a better guess, can't you?");
        add("mirror.archewater.deeper.apeiron", "Something deeper than Arche?");
        add("mirror.archewater.apeiron.0", "[Y]Yes.. and no. You see, the only answer to this was that the true principle of all things lied not in your universe, but outside of it.");
        add("mirror.archewater.apeiron.1", "[Y]It would also have explained how the cosmos and the stars and the nebulae came to be, so far from our own oceans. Multiple breaches between our universe and what was \"outside\".");
        add("mirror.archewater.apeiron.2", "[Y]Academia called it \"Apeiron\". It means \"Infinity\", and it is that which lies, as it is now believed, Beyond the Veil.");
        add("mirror.archewater.apeiron.3", "[Y]It is filtered by the Veil, the Great Dreamer himself, and touches our world through the Void, bringing into existence Arche and all the other deepest oceans in the Cosmos.");
        add("mirror.archewater.apeiron.4", "[Y]Then, when fire wanted to burn on our planet, the Void created a middle plane between Arche and the Overworld, and through what we now call the Nether even fire is born from water, channeled by quartz.");
        add("mirror.archewater.apeiron.5", "[Y]Likewise flared the stars - to which Dagon is elder, for he was born with Arche, oldest among the living.");
        add("mirror.archewater.apeiron.6", "[Y]So everything comes from the waters of Arche, yet even those waters come from Apeiron.");
        add("mirror.archewater.first.end", "(End)");       
        add("mirror.arena.first_branch.0", "[Y]I believe we just found the arena.");
        add("mirror.arena.first_branch.1", "[Y]This is where you can prove your mettle.");
        add("mirror.arena.first.how", "How do I begin the battle?");
        add("mirror.arena.how.0", "[Y]Try interacting with that red block in the corner.");
        add("mirror.arena.how.1", "[Y]Note that the adversaries you'll meet are powerful, and won't go down easily.");
        add("mirror.arena.how.2", "[Y]However, you shouldn't fear death. I think you'll be sent back here in case of defeat, without losing any items.");

        add("effect.folly", "Folly");
        add("effect.terror", "Terror");
        add("effect.heartbreak", "Broken Heart");

        add("memory.unlock.message", "A Memory of %1$s sticks to my mind. I will not forget it.");
        add("memory.unlock.addenda", "This Memory will be found in the \"%1$s\" research in Al Azif.");

        add("memory.animal.name", "Animal");
        add("memory.beheading.name", "Beheading");
        add("memory.change.name", "Change");
        add("memory.crystal.name", "Crystal");
        add("memory.darkness.name", "Darkness");
        add("memory.death.name", "Death");
        add("memory.eldritch.name", "Eldritch");
        add("memory.heartbreak.name", "Heartbreak");
        add("memory.human.name", "Human");
        add("memory.introspection.name", "Introspection");
        add("memory.learning.name", "Learning");
        add("memory.metal.name", "Metal");
        add("memory.plant.name", "Flora");
        add("memory.power.name", "Power");
        add("memory.repair.name", "Mending");
        add("memory.sentience.name", "Sentience");
        add("memory.stillness.name", "Stillness");
        add("memory.tool.name", "Tools");
        add("memory.void.name", "Void");
        add("memory.water.name", "Water");

        add("dreams.timesdreamt", "I have Dreamt %1$s/%2$s times today.");
        add("dreams.maybeinthefuture", "No effect came from this dream, and yet I still felt a tingling. Maybe it'll work in the future, under different circumstances.");
        add("dreams.lowlevel", "I need more strength.");

        add("dreams.groundscan.oreIron", "%1$s blocks of iron ore were found in a %2$sx%3$s area in the ground below%4$s%5$s");
        add("dreams.groundscan.oreGold", "%1$s blocks of gold ore were found in a %2$sx%3$s area in the ground below%4$s%5$s");
        add("dreams.groundscan.oreDiamond", "%1$s blocks of diamond ore were found in a %2$sx%3$s area in the ground below%4$s%5$s");
        add("dreams.groundscan.oreEmerald", "%1$s blocks of emerald ore were found in a %2$sx%3$s area in the ground below%4$s%5$s");
        add("dreams.groundscan.greatestconcentration", ", with the greatest concentration at y-level: %1$s");
        add("dreams.groundscan.block", ". At least one block is located at x: %1$s, y: %2$s, z: %3$s;");

        add("dreams.biomesearch.innsmouth", "Found Voided Biome at x: %1$s, z: %2$s");
        add("dreams.biomesearch.fail", "No Voided biomes found within 1200 block radius");
        add("dreams.biomesearch.hamlet", "An already existing Hamlet was found at x: %1$s, z: %2$s");

        add("dreams.villagesearch.success", "Found Village at x: %1$s, z: %2$s");
        add("dreams.villagesearch.fail", "No Villages found");

        add("dreams.monumentsearch.success", "Found Ocean Monument at x: %1$s, z: %2$s");
        add("dreams.monumentsearch.fail", "No Ocean Monuments found");

        add("dreams.playersearch.success", "Another player's coordinate, either x or z, is: %1$s. I am unaware of what dimension they're in.");
        add("dreams.playersearch.success2", "Another player is located at x: %1$s, z: %2$s. I am unaware of what dimension they're in.");
        add("dreams.playersearch.success3", "Another player is located at x: %1$s, y: %2$s, z: %3$s. I am unaware of what dimension they're in.");
        add("dreams.playersearch.name", "Their name is %1$s.");
        add("dreams.playeritem.success", "Another player is currently wielding: %1$s.");
        add("dreams.playeritem.mildsuccess", "Another player was found, but they were wielding nothing.");
        add("dreams.playeritem.firstletter", "Their name starts with '%1$s'.");
        add("dreams.playersearch.fail", "The Dream was unable to locate another player.");

        add("dreams.deathsearch.teleport", "It seems that casting the Death Dream on myself allows me to teleport to my last death's location.");
        add("dreams.deathsearch.found", "%1$s's last death in the Overworld was at x: %2$s, y: %3$s, z: %4$s.");
        add("dreams.deathsearch.notrecently", "%1$s doesn't seem to have died recently.");
        add("dreams.deathsearch.toostrong", "%1$s's mind is too well shielded to be breached.");

        add("dreams.alienis.almostthere1", "I am certain this aspect will be of great aid very soon.");
        add("dreams.alienis.almostthere2", "But now is not the time. I lack the strength.");
        add("dreams.alienis.needvacuos", "As of now, I need to have dreamt of the Void for this to work.");
        add("dreams.alienissearch.success", "Found Stronghold at x: %1$s, z: %2$s");
        add("dreams.alienissearch.fail", "No Strongholds found");

        add("dreams.animalsearch.nonefound", "No animals of the preferred type were found nearby.");
        add("dreams.animalsearch.toomanyblocks", "To properly summon an animal, I should clear some space nearby, at my height level.");

        add("dreams.introspection.otherplayer", "The Introspection Dream tells me of %1$s.");
        add("dreams.introspection.caster", "The Introspection Dream tells me of myself.");
        add("dreams.introspection.deity", "Chosen Deity: %1$s.");
        add("dreams.introspection.attack", "Attack Multiplier: %1$s       Attack Multiplier in Water: %2$s");
        add("dreams.introspection.defense", "Defense Multiplier: %1$s     Defense Multiplier in Water: %2$s");
        add("dreams.introspection.dream", "Dream Bonus: %1$s.");
        add("dreams.introspection.void", "Has Dreamt of Void.");
        add("dreams.introspection.novoid", "Has not Dreamt of Void.");
        add("dreams.introspection.worship", "Worship Type: %1$s. Worship Strength: %2$s.");
        add("dreams.introspection.noworship", "No Worshippers.");

        add("dreams.endbath", "I think this worked. I should try and commune.");


        add("research.FUMESPREADER.title", "Memories");
        add("research.firstMemory.text", "Make a Memory of Learning.");
        add("research.FUMESPREADER.stage.0", "A new, faint Memory arose from my last Dream.<BR>A Memory of Learning, yet one so faint it could very well not belong to me.<BR>I must exploit it nonetheless. How easily, after all, can Dreams be influenced from the Memories of everyday experiences?<BR>Ah, but I know something very few others do. A Memory can be bottled. Through a Memory Sieve.<BR>A stone basin filled with water, atop of which I will first place an item that could trigger the Memory, then use a memory phial.<BR>In this case, it is a ghast tear that triggers the Memory, though I do not see any possible correlation.<BR>As a side note, where did this knowledge suddenly come from, if not from a memory? Oh, the irony!");
        add("research.FUMESPREADER.stage.1", "I now hold in my hands a collection - or, rather, a recollection - of Learning.<BR>I must Dream under its influence, and to that end I must make a Fume Spreader. A small glass orb that must be placed close to my bed. I must then fill it with Oniric Incense, and only then apply the Memory.<BR>I will then sleep, and the depletion of the Incense will indicate success.<BR>A Memory of Learning, out of any Memory, should indicate me new roads to take.<BR>It has to, for otherwise I'd have no direction left, nowhere to go to.<BR>If this experiment is a failure, then so am I.");

        add("research.CRYSTALDREAMS.title", "Plunging Down");
        add("research.CRYSTALDREAMS.stage.0", "Dreams exist. They are entities, they leave my body, and disappear from my three-dimensional, solid world in a few brief moments, to go where nothing can follow them.<BR>Yet in these few, brief moments.. they go down. Straight down, plunging through stone and bedrock faster than one's eye could follow them. <BR>I feel it. I know it.<BR>I Learned it. <PAGE>I should take at least some advantage of this. For those brief moments I should be able to know more of the soil beneath me. Though a Memory of Learning just won't do this time, I need a different one.<BR>When I mined my first iron ore. Now that's a Memory! Ecstatic I was, as I felt true wealth trickle in my hands!<BR>Heh.. yes, that might cut it.");
        add("research.CRYSTALDREAMS.stage.1", "The Dream reacted to my Memory of Metal by granting me knowledge of the presence of metals in the ground below.<BR>It responded to me. It's as if it was... sentient?<BR>It is bizarre, even somewhat unsettling.. and yet, I can't keep myself from wishing to know more, to shatter this facade of illusions clouding my mind from.. from this unknowable Truth.<BR>I'm sure new experiences will trigger new Memories that will grant new knowledge, and maybe other effects.<BR>I must keep going. Whenever I am stuck, it'll be wise to resort to a Memory of Learning.");
        add("research.CRYSTALDREAMS.addenda.crystal", "Very similarly to Metal, a Memory of Crystal grants me knowledge of the earth beneath me, this time teaching me of the presence of crystals, namely diamonds and emeralds.");
        add("research.CRYSTALDREAMS.addenda.sentience", "Dreams may go straight down, yet their knowledge of the world is vast.<BR>And through the making of a simple book, I'm reminded of those who inhabit this world.<BR>A Memory of Sentience, with which I can instruct my Dream to seek a society like that of a Village, and point me to it.");
        //add("research.metalDream.text", "Dream of Metal.");

        add("research.SLEEPCHAMBER.title", "Sleep Chamber");
        add("research.SLEEPCHAMBER.stage.0", "I'd always been proud of my ability to stay awake and work and walk as long as I needed, leaving sleep to times of absolute necessity.<BR>Oh, how ironic that now it is sleep I need, and can't get enough of it.<BR>I must Dream, keep Dreaming, again and again. Once a day, only at night, is not enough, it just isn't. I shall focus my mind on the creation of some device capable of ridding me of that limit, at least partially.<BR>First, a proper crafting station. The Gear Bench will aid me in the creation of more complex objects.");
        add("research.SLEEPCHAMBER.stage.1", "This machine, which I named Sleep Chamber, is most curious. Its construction requires a knowledge of mechanics, biology and chemistry so far outside of my usual scope of knowledge that I would dare to deem it... alien?<BR>And yet it came to me, all of a sudden, not through experimentation, but seemingly by itself.<BR>Oh, I'll enquire on that mistery another time.<BR>The Chamber must be opened, and once I'm inside I must close it to start Dreaming. It is capable of putting me to sleep once per day, and wake me up immediately, without any time going by. And yet this istant is enough for the Dream to leave me and do its task as dictated by a nearby Fume Spreader, just like a normal bed.<BR>It is truly unbelievable, and yet I feel like I only scraped the surface of what this journey could lead me to.<BR>It is merely a stepping stone, to shatter all the fake truths I thought I knew.");
        add("research.craftedsleep_chamber.text", "Craft a Sleep Chamber.");

        add("research.EFFECTDREAMS.title", "Affecting Myself");
        add("research.EFFECTDREAMS.stage.0", "So far my Dreams have been able to grant me knowledge, whether it be of arcane secrets and enigmas or of something as simple as the very ground below me. <BR>And yet, I believe... NO! I am CERTAIN that there is more they can do.<BR>Maybe I am not yet capable of having my Dreams affect the world around me, but I can't help but wonder whether there is any way for my Dreams to affect.. me? Not my mind, but my body.. <BR>Sounds impossible, but I am far past that now, am I not?");
        add("research.EFFECTDREAMS.stage.1", "The way a Memory influences a Dream is quite unpredictable. They're messages, after all, and how could I fathom the interpretation of such a message from something as bizarre, as alien as.. a Dream?<BR>I will record here those Memories that change the status effects already conditioning my body.");
        add("research.effectDream.text", "Trigger a Memory capable of affecting myself.");
        add("research.EFFECTDREAMS.addenda.power", "A Memory of Power, as I slay a beast of no small stature. A cherished moment, one of satisfaction.<BR>It is through it that my Dream will increase the potency of the effects on my body.<BR>Both positive and negative ones, that is.");
        add("research.EFFECTDREAMS.addenda.stillness", "A Memory of Stillness, as the Sleep Chamber imprisons me time and time again.<BR>The effects on my body, I won't let them leave me. Through this Memory, my Dream will increase their durations. All of them.<BR>They cannot escape. No one can.");
        add("research.EFFECTDREAMS.addenda.change", "A Memory of Change, as I witness a sapling grow into adulthood. Such a small thing, even in its now great size.<BR>Even in Stillness, all things Change. Through this Memory, my Dream will remove the negative effects plaguing my body, and even attempt to convert them into their good counterpart.<BR>A powerful Memory, triggered by such a mundane even. As Truth draws closer, I witness the world in a new light.");

        add("research.HUMANDREAMS.title", "Dreams of Others");
        add("research.HUMANDREAMS.stage.0", "Sometimes, when Dreaming, I get a bizarre feeling.<BR>People like me, even ones I have never known, start appearing in my thoughts. And yet, I feel like it's not a mere construct of my mind, of my subconscious.<BR>No. I think that our Dreams converge within the same point, that they touch each other, and that those presences within my thoughts are REAL.<BR>I believe that, through my \"Dreaming expertise\", I could know more about those others, that I could probe their Dreams while they remain unaware.<BR>I should take advantage of this. I could try to give birth to a new Memory, one that has a social interaction at its core, so as to point my Dream in the direction of others.<BR>Maybe initiating a simple trade with a villager could work?");
        add("research.HUMANDREAMS.stage.1", "I have reason to believe that, within their own weird, a-dimensional plane of existence, Dreams are all adjacent to each other. To all others. At the same time, at all times.<BR>While their position may shift in relation to our world (such as when they go straight down through bedrock), my Dreams are tangent to all others.<BR>This is a breakthrough discovery. As a Dreamer more expert than most, I can probe the Dreams of others with my Own, learning things about them while ignoring any privacy they think they have. I should keep working on this ability, so that it may reach its full potential.<BR>Still, this new knowledge brings with itself a new fear.<BR>Fear that there could be other Dreamers, greater than myself, and that my excessive forays into Dreaming only made me more vulnerable.<BR>Other Dreamers, that could possibly not even be human.<PAGE>A Memory of Human was triggered by initiating a trade with one of those dim-witted Villagers. Sooner or later, I'll make them pay for their inane prices.<BR>This Memory makes for Dreams of no great consequence, for now. It enables me to know of the position of others like me, but just one of either their x or z coordinates, for a single unnamed human other than myself, provided there is at least one at the time of Dreaming.");
        add("research.memHUMAN.text", "Initiate a trade with a Villager, see where it leads.");
        add("research.HUMANDREAMS.addenda.tool", "A Memory of Tools, as I craft a powerful one.<BR>Yet, I burn with envy at the idea of others wielding items mightier than mine.<BR>Through this Memory, I can learn of what item is held in the main hand of a single unnamed human other than myself, provided there is at least one at the time of Dreaming.");

        add("research.INSCRIPTIONS.title", "Tablet Inscriptions");
        add("research.INSCRIPTIONS.stage.0", "These ancient stone tablets can be deciphered.<BR>Their contents may be unsettling, but my pursuit of knowledge won't be stopped by this. I hope.");
        add("research.INSCRIPTIONS.stage.1", "I've managed to decipher these tablets, but I'm sure there are more. I'll record their content here.<BR>When completed, they can be used to learn new research, just like a Memory of Learning. Just like the Memory, they'll only be consumed if there's something to learn.");
        add("research.inscription_complete.text", "Decipher a Tablet.");

        add("research.SLEEPCHAMBERADVANCED.title", "Advanced Sleep Chamber");
        add("research.SLEEPCHAMBERADVANCED.stage.0", "The Sleep Chamber is an extremely useful device, but the progress of research should never stop, it should know no bounds.<BR>New revelations spring to mind on how to make it reach its full potential.");
        add("research.SLEEPCHAMBERADVANCED.stage.1", "The key was WATER. What this substance has to do with Dreaming I can't yet guess, but its addition in the Sleep Chamber's gears and wirings vastly improved its Dream-aiding capabilities.<BR>This Advanced Sleep Chamber lets me Dream twice as many times per day as the regular one. Not only that, it lets my Dream be influenced by two Fume Spreaders at the same time!<BR>Just think of the combinations!");
        add("research.craftedsleep_chamber_advanced.text", "Craft an Advanced Sleep Chamber.");

        add("research.VOIDDREAMS.title", "Dreams of Void");
        add("research.VOIDDREAMS.stage.0", "A new Memory springs forth in my mind.<BR>The Void. Black, inscrutable, unknowable. Its essence is burned in my mind, like no Memory before.<BR>Dreams go through THE VOID.<BR>THAT is where they truly depart from the known world to go where nothing can follow them... yet.<BR>I am certain of this.<BR>I need to Dream of the VOID.<BR>Of its black emptiness.<BR>So I may reach it myself.");
        add("research.VOIDDREAMS.stage.1", "The Dream was far less flagrant that I imagined.<BR>A Dream of Void doesn't seem to affect me directly.<BR>However, were I to Dream again, after Dreaming of Void (or during, if I have the means to do so) then it seems my Dreaming capabilities are somewhat improved. Not terribly so, and it applies only to a scarce few Memories, but it's something.<BR>And yet, I shouldn't underestimate this Memory's importance.<BR>I believe that its true effect stands in slowing down my next Dream's descent, especially its stay in the Void. And if that is true, then my quest for knowledge might have reached a breakthrough.<BR>I NEED to learn of what, or who, is in that black emptiness. To know how Dreams work, where they go, how they gain knowledge. To know what is real and what isn't.<BR>To know the Truth.");
        add("research.voidDream.text", "Dream of emptiness.");

        add("research.FIRSTCONTACT.title", "Contacting the Unknowable");
        add("research.FIRSTCONTACT.stage.0", "If it's true that all Dreams go through the Void, then chances are I am not the only Dreamer whose quest for knowledge focuses there.<BR>It is time to unVeil the Truth. Time for me to contact other Dreamers. No matter the risk, no matter how great or powerful they may be, or what intentions they may have.<BR>No matter what they may be, how different, how alien they may prove to me.<BR>No matter where they come from. From what world, time, reality.<BR>Nothing matters now. This may very well be the end of my research. If it must be like that, so be it, and I hope these notes prove useful to posterity.<BR>I must Dream of Eldritchness, under the influence of Void. I must fix my ears, to hear everything that is told to me. Listen as closely as I can. And be ready for a Dream longer than most.");
        add("research.FIRSTCONTACT.stage.1", "Gracious Gods, don't let my mind crumble as I write this.<BR>A voice. It came from the Ocean. I didn't hear it, I felt it. Through my Dream it touched my body.<BR>It didn't speak to me, but through me, as if it didn't notice I was there, in my Dream.<BR>Yet I know now what I must do. The thought was imprinted in my mind.<BR>I must find the place where the blackness of the Void touches the surface, so I may see it and handle it myself.<BR>But, to be sincere, I do not know whether to continue in my search for knowledge...<BR><BR><BR>Merciful Gods, have mercy upon me.<BR>Don't let me drown.");
        add("research.eldritchDream.text", "Dream of Eldritch.");

        add("research.STRONGHOLD.title", "Finding Strongholds");
        add("research.STRONGHOLD.stage.0", "It is of no surprise that Eldritch Dreams are correlated with the End, in its closeness to the Void.<BR>When not powered by a Void Dream, an Eldritch Dream will tell me the exact location of a single Stronghold.");

        add("research.DARKDREAMS.title", "Dreams of Dark");
        add("research.DARKDREAMS.stage.0", "Before entering the Ocean, I could feel sand beneath my...  beneath my Dream. Dark, slimy sand, the color of Void.<BR>There are indeed reports of damp, blackened beaches, and, furthermore, rumour has it that some of these \"Voided\" biomes are inhabited by small groups of reclusive fishermen.<BR>I wonder if they can provide any clues towards my quest for knowledge. In any case, my goal now is to Dream of Darkness. A new, well imprinted Memory.");
        add("research.DARKDREAMS.stage.1", "Far from perfect, Dreams of Darkness still prove useful in seeking Voided Biomes within limited range.<BR>However, I fear disappointment. Could some black grains of sand truly grant us enlightenment? I think not.<BR>Maybe I should instead focus on seeking the Fishing Hamlets that are rumoured to appear in these places.<BR>I know very well that not all Voided biomes will hold a Hamlet, so my research may take very long. I can comfort myself, knowing how much of the world I'll discover in my travels.<BR>Yet I am left to wonder, how much of it is real, and how much is naught but a wretched lie?<BR>After learning there is so much Beyond our world, so much more than meets the eye.. I am left in agonizing doubt.");
        add("research.darknessDream.text", "Dream of Darkness.");

        add("research.FISHINGHAMLET.title", "The Fishing Hamlet");
        add("research.FISHINGHAMLET.stage.0", "I need the Truth. I am sick of lies.<BR>There is a Veil of illusions clouding everyone's mind, there has to be. Everything I see, is a Lie.<BR>NOTHING BUT A LIE.<BR>I must breach it. I must see Beyond that barrier.<BR>Whether the Fishing Hamlet is the key to the Arcanum, or merely a piece of a grand puzzle, we shall see.");
        add("research.FISHINGHAMLET.stage.1", "This can't be..<BR>All this work, all this research.. led me here?!<BR>To a bunch of degenerates?! Of xenophobic nitwits?!?<BR>Nonono, I can't stop now. I can breach the Veil. I know I can. I know it. I really do.<BR>I need to talk with the Lighthouse Keeper. I hope he proves wiser than his fellow Dwellers.");
        add("research.FISHINGHAMLET.stage.2", "Look at the Ocean.. look at the Ocean..");
        add("research.FindHamlet.text", "Locate a Fishing Hamlet.");
        add("research.LHKeeper.text", "Talk with the Lighthouse Keeper.");

        add("research.IDOL.title", "Communion");
        add("research.IDOL.stage.0", "At times, we think of ourselves as gods. Allmighty, invincible gods to whom the whole world must bow. Gods whose creations could reshape the world entire.<BR>And we're no more than deluded fools.<BR>The Ocean.<BR>Immense, timeless, inscrutable. Could it really care of what we do? Could it really, when it dwarfs our greatest, grandest structures, when it outlives our strongest, toughest contraptions?<BR>A symbol of immensity, ever close, to remind us of our INSIGNIFICANCE.<BR>The Ocean, whose immensity the Hamlet Dwellers seem so fascinated by, is UNKNOWABLE. We could spend whole eternities in the hope of researching its every nook and cranny, and yet vainly, for in its boundlessness, as well as its ever changing, chaotic nature, the things that lie within it.. or beneath it.. will always elude us. Unless they come to us.<BR>Yes, the Ocean must be the key. In its boundless, unkowable immensity, it is SURE to conceal what we're looking for. Some kind of gateway, some sort of door that will let us see Beyond the Veil. In its bottomless depths, we must look for rifts.<BR>Water itself REEKS of Eldritchness, and especially in that Hamlet of bizarre degenerates worshipping some kind of.. of..<BR>Yes. Of.. of course. They're far from degenerates, for they found the solution far before US.<BR>If their Deity truly is a manifestation of what's Beyond the Veil, hiding the Truth from us all, and if their fascination with Dreams and the Ocean is of the same nature as ours.. then we must seek Communion.<BR>Communion with the Great Dreamer.");
        add("research.IDOL.stage.1", "I humbly pledge my life to You, Great Dreamer.<BR>I disavow all my previous beliefs, any gods I may have previously believed in.<BR>I am insignificant to you. The Ocean, Your presence on the Overworld, is symbol of that.<BR>You are unknowable to me. The Void, full of emptiness, is Your inscrutable substance.<BR>Beyond You lie all things. The Truth, hidden by a Veil.<BR>Grant us knowledge, so we may undo all lies.<BR>Grant us strength, so we may survive the Truth.<BR>Grant us courage.. so we may hear Your Holy Voice.");
        add("research.IdolInteract.text", "Pray at the Idol in the Hamlet.");

        add("research.SLUGS.title", "Slugs");
        add("research.SLUGS.stage.0", "To commune with The Great Dreamer is an honor of the highest order. Truly, to have made an impression on Him so as to have sparked His interest is, undoubtedly, the greatest achievement I have ever accomplished.<BR>Yet the strength of the communion is ever faint, for I am weak, and my presence hopelessly bound to the Overworld.<BR>He knows that. He wishes to aid me. Really, He does!<BR>Slimy, shapeless, uncatchable like the water from which they are born. Such are the white slugs on which some Dwellers of the Hamlet feed. They are the key.<BR>I need a bait, and The Great Dreamer provides me with knowledge of how to create one. How ironic, though meaningful, that those shapeless chaotic slugs are so attracted by the perfect, orderly shape of common salt!<BR>The slug bait must be placed in water. There must be no water on top of it, but it must be surrounded by water on its 4 sides. For best performance it should be in the center of an 11x11 square of water.<BR>If the requirements are met, and I am able to see the faintest of particles, I should soon see the watery essence of the slugs on the water surface. They seemingly appear from nothingness, and pop back out of existence in a few moments. I must aim carefully my slug catcher at them, and use it.<BR>Then.. I must eat them. It's the only way. I won't like, but I must.<BR>Once I've eaten a few, say, twenty or so, I must interact with the Idol again.");
        add("research.SLUGS.stage.1", "My Dreams have become stronger, I know it, I know it I know it I KNOW IT.<BR>He heard me, The Great Dreamer HEARD MY CALLS.<BR>I realize now that to worship The Great Dreamer is not just an occasional prayer. It is, instead, a way of life.<BR>From now on, our course of action should be to eat slugs, slugs, and even more slugs. They'll start to taste less and less awful, I am certain.<BR>I must also get closer and closer to the Dwellers, and learn from them. Do deeds which make me more like them. They have known best for generations, after all. And my ancestors couldn't see it.<BR>Once all is done, I must interact with the Idol.");
        add("research.!SLUGS.text", "Eat 20 slugs, interact with Idol.");

        add("research.FISHERMEN.title", "Fishermen");
        add("research.FISHERMEN.stage.0", "First, I'll record here the correct steps for a successful manual use of the bait.<BR>The slug bait must be placed in water. There must be no water on top of it, but it must be surrounded by water on its 4 sides. For best performance it should be in the center of an 11x11 square of water.<BR>If the requirements are met, and I am able to see the faintest of particles, I should soon see the watery essence of the slugs on the water surface. They seemingly appear from nothingness, and pop back out of existence in a few moments. I must aim carefully my slug catcher at them, and use it.<PAGE>Secondly, I am loathe to realize that this manual task will, in the long run, become a bore. The grey robed fishermen in the Hamlet however seem well versed in the art of slug catching.<BR>I can thus employ them to my needs. Interacting with them with a gold ingot in hand will make them follow me, and I'll be able to bring them near to a slug bait, then maybe give them a small nudge to get them on top of it.<BR>Once on the bait, I must ensure they get a steady supply of gold ingots. For each one they get, they'll fish for a minute. The slugs will be dropped on the ground, or get put in a chest adjacent to the Dweller.<BR>I might want to build around them so they do not leave their position.");

        add("research.CANOE.title", "Gifts from the Deep");
        add("research.CANOE.stage.0", "The Hamlet Dwellers' diet, other than slugs, consist mostly of fish. Their barrels are full of cod.<BR>It seems they use their canoes to go fishing. These are built by the carpenters, the Dwellers with dark brown suits who live in two-story houses with normal oak ladders.<BR>Not every Hamlet has carpenters, but they're fairly common.");
        add("research.CANOE.stage.1", "According to the carpenter, the Dweller Fishermen ride their canoes into the Ocean and.. the fish just come to them?<BR>I should attempt that as well, not just in any water but in those of the Ocean itself.<BR>If anything does in fact happen, I shall keep at it for a while, then top it off with a fair deal of slugs. After that, I shall commune.");
        add("research.CANOE.stage.2", "The Ocean itself.. it answers to me, its crashing waves a soothing voice, its blue immensity a vigilant eye, its shapeless water a loving hand.<BR>I have a link with the Ocean now, I am stronger because of it, especially in the water that binds us.<BR>And yet, our link is not strong enough.. I am still a creature of the surface, am I not?");
        add("research.carpenter.text", "Talk with the Hamlet's carpenter.");
        add("research.!CANOE.text", "Sail the Ocean with a Canoe, then commune.");

        add("research.BAPTISM.title", "Baptism");
        add("research.BAPTISM.stage.0", "I was born from water, like all things that be. I live by water, my means of Holy Communion.<BR>So why can't I breathe it? Why does it still hinder my movement?<BR>It makes me strong, and at the same time weakens me.<BR>It grants me life, and yet it can kill me.<BR><BR>The Dwellers though.. they bask in its touch, even its depths. So why can't I?<BR>We must ask the Lighthouse Keeper, one last time.");
        add("research.BAPTISM.stage.1", "I am immortal. Right? I am, am I not?<BR>Maybe it isn't death I fear, but failure. A fate even worse, I reckon. Over and over, it will drive me to madness. And what good is immortality then?<BR>The pool must be tight. One by one wide, and exactly five blocks deep, all of it filled by still water.<BR>I must let myself sink in it, and once I reach the bottom, I must commune, eating a slug.<BR>Then I'll wait. And I will believe, like never before.<BR>Merciful God, don't let me drown.");
        add("research.BAPTISM.stage.2", "I was born from water, like all things that be. I live by water, my means of Holy Communion.<BR>Now, I am water.<BR>Now, I am something. I hope.<BR>To know more of the Truth means to know less of myself. Am I truly a worthless wretch?<BR>My certainties grow weaker.");
        add("research.lhbaptism.text", "Talk with the Lighthouse Keeper.");
        add("research.!BAPTISM.text", "Drown. Then the Idol.");

        add("research.ALLIANCE.title", "Alliance");
        add("research.ALLIANCE.stage.0", "In time, I shall grow yet stronger.<BR>But where is it I go from now?<BR>I must wait for signs. There is nothing I can do by myself.. nothing.");
        add("research.ALLIANCE.stage.1", "DAGON.<BR>Ancient scrolls speak of Him in hushed tones, proclaiming Him the Ruler of the Abyss, King of the Deep Ones.<BR>The first and foremost worshipper of The Great Dreamer.<BR>He noticed me. He sent a Deep One to my aid, though temporarily.<BR>Have I impressed Him? Could He be seeking an Alliance of sorts?<BR>Now that my research leaves the scope of the Dwellers' knowledge, it is time to seek greater aid.<BR>Dagon, I accept Your offer, and I shall speak with You again at the bottom of the Ocean.");
        add("research.ALLIANCE.stage.2", "It RINGS inside of me.<BR>His voice, like a booming drum of war, not asking but COMMANDING.<BR>Three golden blocks I've been given. I shall give them to the grey robed fisherman Dwellers of the Hamlet, then commune.<BR>I have no intention to learn what would happen were I to disobey.<BR>Oh, what have I gotten myself into.");
        add("research.ALLIANCE.stage.3", "By The Great Dreamer.. I was mistaken.<BR>Dagon has offered me a boon like no other.<BR>By holding myself into a stance of power I am capable of summoning a Deep One to my aid. Hold to choose, tap to cast.<BR>Is this power worth it? And the knowledge that comes with it?<BR>I will journey forth. After all.. do I have any choice?");
        add("research.wait.text", "Wait. Hope.");
        add("research.hearing.text", "Eat a slug in the deepest oceans.");
        add("research.!ALLIANCE.text", "Bring the gold to the Dwellers. Commune.");

        add("research.METAMORPHOSIS.title", "Metamorphosis");
        add("research.METAMORPHOSIS.stage.0", "Dagon calls me.<BR>I must speak with Him again.<BR>I cannot escape.");
        add("research.METAMORPHOSIS.stage.1", "The Guardians in the Ocean Monuments.. belong to Dagon?<BR>Yet He asks me to slay them, merely as a test. He must pass Judgement upon me.<BR>What does He mean with \"join His ranks\"? Do I not serve Him already?");
        add("research.METAMORPHOSIS.stage.2", "To whoever is unfortunate enough to come into possession of these writings..<BR>Stop reading for a moment. Look at your skin. Take a second to admire it. Your own, beautiful, candid skin.<BR>Now imagine. Imagine it turning blue. Slowly losing its color. Imagine then seeing it crack and break like dried stone.<BR>Imagine seeing it replaced, replaced by hard blue scales, surrounded by greenish mucus. Slimy, sticky, smelly, this horror is none other than yourself.<BR>Your head thrown forwards, your jaw opening three-ways, your spine bursting into a dorsal fin.<PAGE>I've always held my own self somewhat in contempt, for my insecurities, my failures, my flaws.<BR>This new shape is a blessing.<BR>I am remade in the shape of God. Or, at least, of His Archpriest Dagon.<BR>I am perfect, a wonderful horror.<BR>In my love and hatred for this form, I'll say it's one of power. I acquire great strength and resistance to harm, especially underwater.<BR>I, however, also lose the ability to wear armor and to wield any items other than a handful of marine ones, such as slugs and fish.<BR>There's more to this power than I currently know, I'm sure. I'll need to look for other abilities this shape has.<BR>I thank You, Dagon, for now I am Deep.");
        add("research.hearing2.text", "Eat a slug in the deepest oceans.");
        add("research.!METAMORPHOSIS.text", "Slay three Elder Guardians. Commune.");

        add("research.DREAMBOTTLE.title", "Dream Bottle");
        add("research.DREAMBOTTLE.stage.0", "I shed a tear when I was baptised.<BR>A sliver of Truth, it was, reminding me of my worthlessness.<BR>And yet, it was that very tear that led me to *know*.<BR>I cannot amass my own tears, I am no professional Weeper, am I?<BR>Am I?<BR>I'll seek help in bodies other than mine.");
        add("research.DREAMBOTTLE.stage.1", "Through Weepers and Fleti I am granted knowledge, and through Dreams I shall utilize and magnify such knowledge.<BR>A Dream Bottle is what I must craft. An invaluable tool that will let me Dream limitless times per day, with multiple Memories at a time.<BR>The only price will be tears. But not my own.");
        add("research.DREAMBOTTLE.stage.2", "The Dream Bottle stores tears. It can be used on a Lacrymatory to be filled up to its maximum capacity, and used again to be emptied as much as possible.<BR>It can store up to 4 buckets of tears.<BR>It can then be used without sneaking to be filled with Memories, up to four.<BR>Now, whenever I drink the Bottle's contents while sneaking I will be able to Dream anywhere, anytime, as many times as I want.<BR>One bucket of tears for every Dream, that's the exchange rate.<BR>As I learn of this new tool, new possibilities open up to me in the art of Dreaming.");
        add("research.gotweepers.text", "Find a way to gather tears.");
        add("research.crafteddream_bottle.text", "Craft a Dream Bottle.");
        add("research.DREAMBOTTLE.addenda.statue", "Addendum:<BR>It seems that being worshipped through the use of a statue slightly improves the Dream-to-Tears efficiency of the Bottle.");

        add("research.WATERWALKING.title", "Water Walking");
        add("research.WATERWALKING.stage.0", "Great Father Dagon, I am coming.");
        add("research.WATERWALKING.stage.1", "The End is engulfed in Void, the outer bulwark of the Veil, of The Great Dreamer Himself.<BR>It is there that I must Dream, while in water. I must Dream of Darkness, of Eldritch, of Learning and of Void, all at the same time. I know this, somehow.<BR>I cannot use a bed or a Sleep Chamber while in water, so I must take a third route to Dream.");
        add("research.WATERWALKING.stage.2", "Water Walking is the art of becoming one with the Ocean.<BR>When I use the power, while in water, I will be transported to any location or entity I am looking at, provided they are also in water.<BR>Additionally, using it while sneaking will set a waypoint. Doing that again will transport me back to that waypoint, then deleting it.<BR>Truly, I am now ever closer to the Ocean, a part of its very essence.<BR>A tiny, insignificant part in its unknowable immensity.");
        add("research.hearing3.text", "Eat a slug in the deepest oceans.");
        add("research.!WATERWALKING.text", "Bathe in the End and Dream. Then Commune.");

        add("research.WATERYCRADLE.title", "Watery Cradle");
        add("research.WATERYCRADLE.stage.0", "Useless worms plague this world, living in their placid isles of ignorance.<BR>The Great Dreamer teaches me how best to employ them.<BR>I will make their bodies, maybe their corpses, mine to experiment upon.<BR>They are insignificant, after all.");
        add("research.WATERYCRADLE.stage.1", "Villagers don't serve much use to the world, do they?<BR>Now they will. Through me.<BR>The Watery Cradle, where patients drift off in Dreams, giving me right and duty to do with their bodies what I wish, through the use of Surgery Tools.<BR>Three units of a Watery Cradle must be put in a line, then I shall interact with one of its ends.<BR>Patients will come through the use of a very simple blackjack, made to stun them into unconsciousness. Using it upon a Villager will put him on the ground, ready to be picked up by interacting with him while sneaking.<BR>I can then place the Held Villager into the Watery Cradle, and find ways to experiment.<BR>It is none other than The Great Dreamer Himself who grants this knowledge upon me. Oh, the honor!");
        add("research.craftedwatery_cradle.text", "Craft three Watery Cradles.");

        add("research.SPINES.title", "Ripping spines");
        add("research.SPINES.stage.0", "A god among men is no different from a man among worms.<BR>Rip their spines and make them CRAWL.");
        add("research.SPINES.stage.1", "Willpower resides in the spine. Without it, the Villager will never get up, never rebel.<BR>The Villager is defenseless, in body and mind. No order of mine will remain unanswered.<BR>Problem is, there is nothing for them to do, in this new status of even greater uselessness. I must find a way to employ their servitude.<BR>Meanwhile, the spines themselves are an interesting boon. Besides the obvious bonemeal they provide, they are sources of willpower, the will to keep standing.<BR>And of fear. A ripped spine is no pretty sight, until one gets used to it that is.<BR>I'll surely find uses for them, soon enough.");
        add("research.extractedspine.text", "Rip a spine.");

        add("research.HEARTS.title", "Tearing hearts");
        add("research.HEARTS.stage.0", "Blood is life.<BR>Blood is water. Water is life.<BR>The relation is clear. And so is the irony.<BR>To gain life, I must grant death.");
        add("research.HEARTS.stage.1", "A heartless Villager has no life left to live. He'll expire in moments.<BR>Their hearts, however, will keep living, beating.<BR>When placed on the ground, the heart will attract undead, for they seek life.<BR>And yet, as a symbol of life, I am sure there's more that can be done with them.");
        add("research.tornheart.text", "Tear a heart.");

        add("research.WEEPERS.title", "Weepers");
        add("research.WEEPERS.stage.0", "The person I once was would've reeled in disgust at the idea of ripping spines and tearing hearts. Now I barely flinch.<BR>But this..? This new idea that springs forth in my mind, through the uncanny doings of The Great Dreamer? Even now my hands tremble.<BR>To fill their brains with water, to make them SEE things as they truly are. Their brains filled, so they may only weep.");
        add("research.WEEPERS.stage.1", "A tear is a revelation. All should fear the ones who weep, for they know more than most.<BR>A Weeper is a Villager whose head was enlarged to monstruos size, filled by water.<BR>A Fletum is a Weeper who's lost its spine. It needs no body, it is whole within its head.<BR>They Dream, they learn, they weep. I must harvest their tears, they are knowledge.");
        add("research.WEEPERS.stage.2", "It is most natural to weep when one learns something. The sudden realization of one's own insignificance is no small deal, after all.<BR>It should not be held back, nor frowned upon, for tears are precious. A sign of humility before the Truth, and all that it entails.<BR>A Lacrymatory is where one should weep, in case one wishes to preserve their tears, to be then picked up through buckets. For some reason, when fallen into the Lacrymatory their power does not expire.<BR>And so it is, that Weepers and Fleti come to usefulness. A Weeper can be picked up directly through the use of a blackjack, whereas a Fletum requires only my bare hands, while sneaking, disgusting as it may be.<BR>Both of them can be used upon the Lacrymatory, and they'll weep in it. They must not wander far, and thus it is important to build around them so they don't escape.<BR>Additionally, only one of them can be used per Lacrymatory, and no two of them, whether they be Weeper or Fletum, should be too close to each other. No less than three blocks, per say.<BR>After all, how many times have I cried in my loneliness?");
        add("research.filledtears.text", "Fill a brain with water.");
        add("research.craftedlacrymatory.text", "Craft a Lacrymatory.");
        add("research.WEEPERS.addenda.bottle", "Addendum:<BR>There are snippets of Truth among the Weepers' mad ravings. I would do best to get close to them, to grasp onto any secrets they may share.");

        add("research.REVELATIONRING.title", "Ring of Revelation");
        add("research.REVELATIONRING.stage.0", "Weepers and Fleti lack eyes, yet they see more than most.<BR>Their tears are precious, and I shall dampen a ring with them.");
        add("research.REVELATIONRING.stage.1", "The Ring of Revelation lets me see the unseen.<BR>When its passive effect is turned on, all invisible entities shall be revealed to me.<BR>When its active power is used, all such entities will outright lose their invisibility, leaving them for the whole world to see.<BR>Nothing shall lie unseen to me again.");
        add("research.craftedrevelation_ring.text", "Craft a Ring of Revelation.");

        add("research.BLEEDINGBELT.title", "Bleeding Belt");
        add("research.BLEEDINGBELT.stage.0", "As my knowledge grows, so does my life become more precious.<BR>It must be preserved.<BR>I will gladly make sacrifices, of all kinds.<BR>Blood is the key.");
        add("research.BLEEDINGBELT.stage.1", "When worn, the Bleeding Belt oozes the dark red fluid, of unknown origin for it isn't mine.<BR>What I do know is that, when its passive effect is enabled, it bathes me with life in exchange for hunger.<BR>Any time I am struck, my health shall be unharmed for as long as my stomach isn't empty.");
        add("research.craftedbleeding_belt.text", "Craft a Bleeding Belt.");

        add("research.BONETIARA.title", "Bone Tiara");
        add("research.BONETIARA.stage.0", "If it is true that one must fight fire with fire, then I shall be the first to fight fear with fear.<BR>No terrors shall break my will.");
        add("research.BONETIARA.stage.1", "The things I see as my knowledge grows send shivers down my spine.<BR>Hopefully, they'll now send them through someone else's.<BR>A tiara of bones will now adorn my head. As long as its passive effect is on, I shall be immune to Terror and Folly. My will will not break.<BR>Not yet.");
        add("research.craftedbone_tiara.text", "Craft a Bone Tiara.");

        add("research.BLOODCOVENANT.title", "Covenant of Blood");
        add("research.BLOODCOVENANT.stage.0", "Through blood, different lives are linked.<BR>The dark red fluid is not of this world, and the links are of eldritch nature. I may find use for such bonds, yet this time I cannot do it alone. I need other Players.");
        add("research.BLOODCOVENANT.stage.1", "The relation between blood and water grows ever closer.<BR>All life came from water, after it turned to blood. All life is linked by this one origin.<BR>The Covenant of Blood is based upon these links. When I activate it while submerged in water, I will for a few brief moments see all other Players who, like me, wear the ring and are waterborne.<BR>When looking at one of them, activating the ring again will let me instantaneously travel to my ally.");
        add("research.craftedblood_covenant.text", "Craft a Covenant of Blood.");

        add("research.BLOODCROWN.title", "Crown of Thorns");
        add("research.BLOODCROWN.stage.0", "If I am ever to be struck down, I want it to be by my own hand.<BR>Until then, I must defy death. Again and again.<BR>I need the WILL to LIVE.");
        add("research.BLOODCROWN.stage.1", "Sprinkled with blood and dust of dorsal spines, the Crown of Thorns is a thing of horror.<BR>When its passive effect is active, it grants me the will and strength to keep standing after a blow that would've been final, piercing my forehead and bathing me in my own blood.<BR>Yet no boon comes without a price. After the effect takes hold I will be mortal again for a short period, at the end of which the damage that was negated will finally be dealt.<BR>I must use the Crown wisely, and be ready to heal.");
        add("research.craftedblood_crown.text", "Craft a Crown of Thorns.");

        add("research.AZACNOCHARM.title", "Charm of Azacno");
        add("research.AZACNOCHARM.stage.0", "Aeons ago, a great parasite fell from the Cosmos.<BR>Unknown in shape and intent, it laid its nest in the deepest Oceans.<BR>Sucking blood, bringing fear and granting knowledge. To know him is to reach a culmination of my experiments.");
        add("research.AZACNOCHARM.stage.1", "Azacno was born from a rift in the Veil, yet not one from below our Oceans.<BR>Instead, he came from the stars, crashing into our waters, and communing with The Great Dreamer.<BR>He is of parasitical nature, and so are his followers. The Charm of Azacno is a feared talisman that infects the enemies of its wearer.<BR>Using its active power grants me for a few seconds the ability to infect others, unless they wear the Charm themselves.<BR>Monsters, animals and other mindless beasts will simply be driven back by the terror instilled by the infection.<BR>It is on other Players, however, that the effect is most terrible.<BR>An infected Player slowly succumbs more and more to the infection, as it grows worse and worse. In the end, the effects may even become visible to the eye.<BR>A fate that not even death will save one from. I do not know of any cures yet.<BR>It is of note that this charm is not magical in nature. It is merely a symbol by which Azacno recognizes me as one of his sect, and grants me this power.<BR>It is important I seek alliance with those others who worship The Great Dreamer.");
        add("research.craftedazacno_charm.text", "Craft a Charm of Azacno.");

        add("research.STATUE.title", "Worship");
        add("research.STATUE.stage.0", "Those worthless wretches, the spineless Villagers, need not be turned into Fleti to be of use.<BR>Bound to the ground as they are, they may think themselves helpless and useless. Fool's worries, I say! All they need is a guide.<BR>Or, rather, a God.<BR>I will gladly lend myself to the cause.<BR>A symbol is needed. Say, a Statue.<BR>Then, a link. My blood will do.<BR>Blood makes for powerful binding, after all.");
        add("research.STATUE.stage.1", "How pretty. It depicts me as I pray and appease my own God.<BR>I must set an example for my followers, don't I?<BR>The Statue must be placed, then surrounded with crawling Villagers, their spines ripped and their wills broken.<BR>Of course, I must also make sure they don't flee from duty. They are to be imprisoned.<BR>Lastly, I need to cut myself on the Statue, to link it to me. It is an act of self sacrifice.<BR>I must set an example, after all.");
        add("research.STATUE.stage.2", "I feel them. Their prayers.<BR>This must be what it's like to be a GOD.<BR>They leave me strengthened, my powers invigorated.<BR>For instance, more Deep Ones can be summoned through my pact with Dagon.<BR>It takes less time for me to recover from activating Powers or Baubles. Even Dreams seem to grow stronger.<BR>Only a single Statue can be used at a time, but that is enough.<BR>I, now, am truly godly. I am grand, mighty, I am.. I am..<BR>..do I deserve all this? Or have I sinned of arrogance?");
        add("research.craftedstatue.text", "Craft a Statue.");
        add("research.linkedstatue.text", "Link a Statue to myself.");

        add("research.SACRIFICESTATUE.title", "Statue of Sacrifice");
        add("research.SACRIFICESTATUE.stage.0", "Could I seek meaning in selflessness?<BR>To pay myself for someone else's wellbeing? Quite a change from my previous attitudes, and yet, I have followers to take care of now!<BR>I shall build a Statue that portrays me in a very different position, then.");
        add("research.SACRIFICESTATUE.stage.1", "I feel a bit better with myself now, and that's quite something in this journey of hopelessness.<BR>The Statue of Sacrifice does everything the normal Statue does, though it takes slighty more crawlers to achieve the same power.<BR>However, when I'm under its effects, I can use the Sacrificial Altar by using a Sacrificial Knife upon it, instead of sacrificing a Villager, and holding the stack in my other hand instead of dropping it on the ground.<BR>Additionally, every time I take damage all my nearby combat minions (such as summoned Deep Ones, and the like) are strengthened and healed.<BR>Lastly, my patients in the Watery Cradle seem truly inspired by my acts. So much so, that they follow in my footsteps of sacrifice and at times choose to relinquish two hearts, instead of one! However that may be possible.<BR>An example, that's what I must be! Even if it means pain!<BR>After all, I do deserve some Penitence.");
        add("research.craftedsacrifice_statue.text", "Craft a Statue of Sacrifice.");

        add("research.PENITENCESTATUE.title", "Statue of Penitence");
        add("research.PENITENCESTATUE.stage.0", "I have sinned. I have sinned.<BR>In my arrogance I forgot my insignificance.<BR>I must right these wrongs. With blood.<BR>My own.<BR>I will build a new Statue, one that shows my regret.");
        add("research.PENITENCESTATUE.stage.1", "This feels more.. right.<BR>I wanted to play God, despite being so, so tiny. It's only right that I suffer some punishment.<BR>The Statue of Penitence does everything the normal Statue does, though it takes slighty more crawlers to achieve the same power.<BR>However, when I'm under its effects, I am reminded of my own flaws, my failures, my weaknesses.<BR>With a Sacrificial Knife, I will cut myself while wielding a sword in the main hand, for I've always been weak.<BR>I will cut when I wield a shovel, an axe, a pickaxe, for I've always been slow.<BR>Even the hoe.. for the green thumb I never had.<BR>When the worshippers are enough, even the Crown of Thorns may become easier to use.<BR>The way of pain is an arduous one, yet not impossible.<BR>All it takes is a little Sacrifice.");
        add("research.craftedpenitence_statue.text", "Craft a Statue of Penitence.");

        add("research.VALOURSTATUE.title", "Statue of Valour");
        add("research.VALOURSTATUE.stage.0", "There is no such thing as Valour.<BR>And even if there were, I'd be no paragon.<BR>This research is a dead end.");

        add("research.SACRIFICEALTAR.title", "Altar of Sacrifice");
        add("research.SACRIFICEALTAR.stage.0", "I revel in limitless gratitude towards my Patron, yet in agony.<BR>For there is no way I can duly offer Him my thanks.<BR>Or is there?<BR>Blood makes for powerful bonds. It is water and life, all at once.<BR>It is the link that binds all things living to the deepest oceans.<BR>Through blood shall I thank Him, through life and water.<BR>An Altar of Sacrifice, of Hearts and Prismarine.<BR>First, a core.");
        add("research.SACRIFICEALTAR.stage.1", "I must build a structure, as described below. The hearts will be expended for every sacrifice. I can interact with bare hands with the Core to make sure it's been built correctly.<BR>A heartless villager is to be placed upon the Core. He'll expire in seconds. Then, a boon.<BR>My offerings will be rewarded. Dropping items on the altar before the sacrifice might turn them into something else.<BR>For instance, stone bricks become blood in color and essence. A new resource, one of unknown properties.<BR>A sacrifice without valid offerings will result in a health boost.<BR>I thank You, Great Dreamer, with all my hearts.");
        add("research.craftedsacrifice_altar.text", "Craft a Sacrificial Altar Core.");

        add("research.CORALSTAFF.title", "Coral Staff");
        add("research.CORALSTAFF.stage.0", "In their unholy penitence, undead seek life.<BR>However, they're imbeciles.<BR>It shouldn't be a difficult task to redirect them from one beating heart to the next, their \"minds\" fooled by what seems better flesh.<BR>I *know* the exact tool I need.");
        add("research.CORALSTAFF.stage.1", "How bizarre it is that the corals of the deep look so similar to our own arteries.<BR>No coincidence, I am certain. The link between the two is strong.<BR>And it is through this very staff that I may bind hearts together. Interacting with a placed heart while sneaking will store its position in the staff. Once done, interacting while sneaking with another heart (within no more than 32 blocks of distance) will bind the old one's position to this new one's. Any undead that are attracted to the first will then be redirected to the second, which may be also linked to yet another heart, resulting in a true procession of the damned.<BR>If the staff stores a position, it may be cleared by using it while sneaking on the very same heart it was taken from, or while aiming in the air. Using the staff without sneaking will give info on itself or on a clicked heart.<BR>There is, in truth, another use to the staff: attacking an enemy with it will result in nearby undead targeting it, in their endless pursuit of blood and life.");
        add("research.craftedstaff.text", "Craft a Coral Staff.");

        add("research.BLOODWELL.title", "Blood Well");
        add("research.BLOODWELL.stage.0", "If the undead's desire for blood is so great, then I shall answer to that desire as best as I may.<BR>I shall cover them with the sweet, red substance.<BR>I will build a well that will hoard such undead, bind them to me, so that I may rule them.<BR>A simple structure of blood bricks. The heart must be placed last.");
        add("research.BLOODWELL.stage.1", "The Well still beats. The dead are drawn towards it, even more so than to normal hearts.<BR>Once they enter the Well, the dead are gone, stored in it. Or, rather, through it.<BR>Blood Sigils can be made for both Zombies and Skeletons. When these undead enter the Well they can later be reacquired through the use of Sigils by using these on the central block of the Well. The Sigils can then be used on the ground to summon my own Blood Undead.<BR>Sigils can be emptied in the Well by sneaking. Summoned Blood Undead can be put back in the Sigil by using it upon them.<BR>I do not know how the Well stores the dead. I imagine it could be a link to some other location, in or out of this world. I truly wish I could enter it myself.<BR>Alas, I am not dead as of yet.");
        add("research.builtwell.text", "Build a Blood Well.");

        add("research.SHOGGOTH.title", "Shoggoth");
        add("research.SHOGGOTH.stage.0", "Shoggoths.<BR>Shapeless masses of flesh, washed away aeons ago on the Sea's shores.<BR>Slaves to the Elder Things, builders of cities.<BR>They're born from tears.<BR>I must create a Shoggoth Vat. That is, a 3x3x3 cube of tears, making sure each block is filled. Then three Fleti must be placed in it.<BR>Once all is done, I must enter it, and through a Dream Bottle I must Dream simultaneously of Darkness, Eldritchness, Learning and Void.<BR>A Shoggoth is no gentle creature. It will attack all animals and all Players other than its owner on sight. If it becomes too delighted in killing, then not even its owner is safe.<BR>A Shoggoth is to be picked up through a blackjack, provided that it is sufficiently calm.");

        add("research.CITYMAPPER.title", "City Mapping");
        add("research.CITYMAPPER.stage.0", "Shoggoths once built cities.<BR>They shall do so again.");
        add("research.CITYMAPPER.stage.1", "A City Mapper can create a map schematic that is then to be given to a Shoggoth.<BR>I can place buildings from the menu on the right onto the map. Buildings must not intersect each other and must be fully contained within the map's borders.<BR>Buildings can be rotated before or after placement with the mouse wheel.<BR>Buildings on the map can be moved by clicking on them and deleted by moving them outside the map.<BR>I must save changes before exiting the Mapper and before creating the schematic.<BR>Once the schematic is made, I can then give it to a Shoggoth.<BR>Of course, I must also make sure the Shoggoth isn't distracted by any animals, other Players or other Shoggoths.<BR>As of now, there's only a handful of block types the Shoggoth can create. Maybe in the future I'll find more.<BR>The Shoggoth creates such blocks by itself, but I can synthesize them myself as well, if needs be.<BR>Lastly, I should remember to light up buildings soon after they're built, as they have no source of lighting themselves.");
        add("research.craftedcity_mapper.text", "Craft a City Mapper.");

        add("research.CLINIC.title", "Clinic");
        add("research.CLINIC.stage.0", "The Clinic seems to have been designed specifically for Weepers and Fleti, it seems.<BR>A bizarre discovery, given how ancient its design must be. How long have Weepers existed for?<BR>Its interior is split in multiple chambers. Each one contains a chiseled block in the floor, where a Lacrymatory is meant to be placed.");

        add("research.DEATHDREAM.title", "Dreams of Death");
        add("research.DEATHDREAM.stage.0", "A Dream of Death, as I respawn once again.<BR>Yet I am not the only one that cannot truly die.<BR>With this Memory, I can invade the privacy of another random Player, and discover the last place where they died, hopefully profiting off their spoils.<BR>No grave is too sacred.");
        add("research.DEATHDREAM.addenda.teleport", "It seems that targeting myself with a Death Dream, through a Player Sigil bound to myself used on the Dream Shrine, allows me to outright teleport to my last Overworld death's location.<BR>But how? How can a simple Dream shift my whole body?<BR>Could this be a key to breaching the Veil?");

        add("research.REPAIRDREAM.title", "Dreams of Mending");
        add("research.REPAIRDREAM.stage.0", "A Dream of Mending, as I smite the hammer on the anvil.<BR>I cannot truly die, yet many of my tools are fraught with mortality. This Memory influences the very matter I am surrounded with, and repairs the items I am wearing and wielding.");

        add("research.ANIMALDREAM.title", "Dreams of Animals");
        add("research.ANIMALDREAM.stage.0", "A Dream of Animal, as I feed one with my own hands.<BR>Dropping a specific item near to me before I Dream of this Memory will spawn nearby an animal related to that item.<BR>Where my Dream plucks that creature from, I can only speculate.<PAGE>Examples of usable items would be porkchop for pigs, bones for wolves, leather for cows, horses and even llamas. Of course there are more to discover.");

        add("research.DREAMSHRINE.title", "Dream Shrine");
        add("research.DREAMSHRINE.stage.0", "The Nether lies halfway between the Overworld and the Deepest Oceans. Surrounded by Void on both sides, it is closer to the true Void, the true Veil that lies beneath All.<BR>Even that accursed land of fire was born from water.<BR>Like all things that be.<BR>And even though it's small in surface compared to the Overworld, some Dreams go through it still.<BR>Quartz makes for a powerful conductor, not only of redstone but even of Dreams.<BR>A Shrine of Dreams, made of Quartz, to cast them wherever I desire. That is my new goal.<BR>All I require is a way to bind specific locations.. even specific people.. to focus my Dreams towards them.");
        add("research.DREAMSHRINE.stage.1", "The Dream Shrine is to be used in tandem with the Dream Bottle, as well as a Pathway or Player Blood Sigil.<BR>The correct creation of the structure can be verified by using on the central chiseled quartz block either one of those sigils.<BR>While standing exactly on top of the central chiseled quartz block, by holding the Dream Bottle in my main hand and the bound sigil in the offhand I am capable of casting Dreams towards specific locations or people.<BR>As an example, Dreams that inspect the ground will work as if they were used atop the location or Player bound in the sigil.<BR>Dreams that targeted random Players can now target specific ones, and even certain Dreams that would only affect myself can now be utilized upon others.<BR>From my base I am now able to see and command all kinds of things. A precious knowledge, one that must be kept tight and silent.");
        add("research.boundpathway2.text", "Find a way to bind locations.");

        add("research.BLOODSIGILS.title", "Blood Sigils");
        add("research.BLOODSIGILS.stage.0", "Through blood we're bound not just to the Deepest Oceans, but to All.<BR>A Pathway Sigil can be used to bind to itself a location in the world, simply by using it.<BR>I should try it before bringing my research forwards.");
        add("research.BLOODSIGILS.stage.1", "Indeed, the Blood Sigil has forged an unbreakable bond.<BR>Through these Pathway Sigils I will be able to target specific locations.<BR>There is, however, another type of Sigil: the Player Sigil, that can bind a specific person to itself.<BR>More difficult to use, this one requires to be held in my hands, either main or offhand, as I injure another Player enough for them to bleed.<BR>Once their blood is in my hands, so too shall their fate be mine to dictate.");
        add("research.boundpathway.text", "Make a Pathway Sigil.");

        add("research.PLANTDREAMS.title", "Dreams of Flora");
        add("research.PLANTDREAMS.stage.0", "A Memory of Flora, as I grow more and more plants through bone meal.<BR>What could such a Dream entail?");
        add("research.PLANTDREAMS.stage.1", "I got an apple.<BR>Started Dreaming, woke up, an apple was in my hands.<BR>It came from nowhere. A mere Dream, capable of creating, or at least transporting matter?<BR>The implications are colossal, but also terrible.<BR>What could I truly achieve through Dreams? And what could other Dreamers do to me, were I to catch their attention?<BR>It's frightening.<BR>But hey, I got an apple!<BR>This Dream deserves further enquiry.");
        add("research.plantDream.text", "Dream of Flora.");

        add("research.VANILLASEEDS.title", "Grass Seeds");
        add("research.VANILLASEEDS.stage.0", "The Flora Dream granted me a new kind of crop.<BR>These grass seeds are meant to be cultivated like normal wheat by placing them on tilled soil.<BR>The difference stands in the seeds themselves being the actual crop.<BR>Indeed, when used upon normal dirt these seeds are capable of covering it with lush grass. They are expended in the act, however.<BR>This first bunch of seeds should be used immediately on tilled soil to gain more of their kind, as I do not know whether further Flora Dreams will grant me the same type of seed.");

        add("research.REDSTONESEEDS.title", "Redstone Seeds");
        add("research.REDSTONESEEDS.stage.0", "These seeds are more special than their grass counterpart.<BR>They are cultivated in the same way, yet these have different effects when utilized.<BR>They can be used upon both dirt and grass. When that happens, the grass is converted into Redstone Grass, a mimic type of block that looks exactly like normal grass, but produces a redstone signal when an entity stands upon it.<BR>An invisible trap, to catch unwanted intruders.");

        add("research.GHOSTSEEDS.title", "Ghost Seeds");
        add("research.GHOSTSEEDS.stage.0", "These seeds are extremely light. They feel almost like air in my very own hands.<BR>They can be cultivated on tilled soil like the other two types of seeds.<BR>However, when used upon dirt or grass these seeds will transform that block into ghost grass, an ethereal block that is insubstantial on touch, but looks exactly like grass to the eye. Perfect for pitfalls and the like.<BR>Only now, however, do I reflect on the weirdness of these finds. These seeds are too bizarre to belong to this world.<BR>So where does the Dream go to pluck such treasures?");

        add("research.ARBOREALGENERATOR.title", "Arboreal Generator");
        add("research.ARBOREALGENERATOR.stage.0", "Next time I Dream of Flora, I shall hold a diamond in my hand. Then something will happen.<BR>How do I know this? Well, it's that that I do not know.");
        add("research.ARBOREALGENERATOR.stage.1", "This small, pretty tree is known as an Arboreal Generator.<BR>It is a useful plant, that slowly generates saplings on each of its four sides.<BR>It first creates small shrubs. These emit an extremely faint redstone signal, which might come in useful.<BR>These shrubs are then turned into saplings of various kinds.<BR>This plant does not belong to this world, I am certain.");
        add("research.plantgenerator.text", "Dream of Flora with a diamond in hand.");

        add("research.VIJHISS.title", "Vijhiss");
        add("research.VIJHISS.stage.0", "My next Flora Dream should be made with a block of obsidian in my hand.<BR>I *know* something is going to happen.");
        add("research.VIJHISS.stage.1", "This plant is somewhat unsettling.<BR>Not only because of its appearance, betraying no sense of belonging to this world.<BR>No, this plant is greedy.<BR>In the most literal sense, in fact!<BR>Vijhiss wants and accepts gold. Ingots fashioned from the noble yellow metal will solicit a reward from the plant.<BR>Vijhiss is capable of granting me numerous kinds of seeds, some from this world and some not, some that I have seen already and some entirely new.<BR>My only worry lies in the faint sense of glee that the plant emanates at every trade.");
        add("research.plantvijhiss.text", "Dream of Flora with obsidian in hand.");

        add("research.MEGYDREA.title", "Megydrea");
        add("research.MEGYDREA.stage.0", "Should I encourage the avid nature of Vijhiss?<BR>I don't trust that plant, yet the boons may be worth it.<BR>I'll try and give it a bigger piece of gold.");
        add("research.MEGYDREA.stage.1", "A new plant, granted to me by Vijhiss. I can obtain more of its kind by giving more gold blocks to that greedy weed.<BR>The Megydrea acts as a fluid tank. It is placed on the ceiling and hangs from there, two blocks tall.<BR>Up to forty buckets of any fluid can be stored.");
        add("research.gotmegydrea.text", "Give Vijhiss a gold block.");

        add("research.BEHEADINGDREAM.title", "Dreams of Beheading");
        add("research.BEHEADINGDREAM.stage.0", "A Memory of Beheading, as I cut off a wither skull.<BR>The thought is macabre, yet the death is quick and painless.<BR>Such a Memory can be used to gain my own head. Not a copy, I can swear it breathed up to mere moments ago.<BR>If used on the Dream Shrine while targeted towards a certain Player, it can be instead used to gain that Player's head.<BR>Do these heads not belong to this world? Or even this time?");

        add("research.HEARTBREAKDREAM.title", "Dreams of Heartbreak");
        add("research.HEARTBREAKDREAM.stage.0", "A Memory of Heartbreak, as I lose a loyal friend.<BR>Slowly, sorrow is replaced with anger. Towards those who don't feel my pain, closed in their own cynicism.<BR>A tinge of hypocrisy there?<BR>Shush.<BR>A Memory of Heartbreak makes for a far more offensive Dream than most. When used all non Player creatures within a fairly large distance, as well as I, remain afflicted for a limited time. Our health will be lowered until the effect ends, and a proper combination with Memories of Power and of Stillness makes for an extremely powerful weapon.<BR>Of course, hurting myself in the process is not ideal. With the Dream Shrine, I can properly target specific locations to afflict all the non Player creatures in a 30 block radius centered on the Sigil's location without being penalized for it.<BR>And, of course, I may also target specific Players.<BR>Everyone's had dark periods in their life. I must only make them reminisce.");

        add("research.FINALQUEST.title", "A Snippet Of Truth");
        add("research.FINALQUEST.stage.0", "I am close to a breach.<BR>Dagon calls.");
        add("research.FINALQUEST.stage.1", "The good keeper helped me so much.<BR>But there are countless more just like him. His death won't matter.<BR>Should I hear his last words?");
        add("research.FINALQUEST.stage.2", "No godly boon this time. Only a snippet of Truth, a knowledge that stings.<BR>Should I keep going? Will it matter?");
        add("research.hearing4.text", "Eat a slug in the deepest oceans.");
        add("research.killedkeeper.text", "Murder the Lighthouse Keeper.");

        add("research.FLUTE.title", "Flute Of The Outer Gods");
        add("research.FLUTE.stage.0", "This Flute does not belong to this world. It weighs nothing, and it leaves a faint echo of unearthly melody.<BR>Although I am hesitant to touch it with my mouth, its effects are powerful.<BR>When playing it all creatures around me, even other Players, are taken by Folly, and writhe in madness.<BR>Even I could be affected, if my will didn't prove strong enough.<BR>Some creatures may be affected in more horrid ways. I should be careful.");

        add("research.SAWCLEAVER.title", "Saw Cleaver");
        add("research.SAWCLEAVER.stage.0", "The Saw Cleaver was used by monster hunters in ages long past.<BR>My own ancestors.<BR>When used it switches between a contracted and elongated form. The former works like any sword, the latter allows me to hit all enemies around me at the cost of less damage and no knockback. Switching to the extended form while in combat will also result in an attack, if I time it right.<BR>Additionally, when wielding this weapon I am able to dodge like its ancient owners. By tapping the Dodge Key ('Alt' by default) I can quick step in the chosen direction.<BR>Yet, I am taken by doubt. Should I really follow in my ancestors' footsteps?");

        add("research.WOLFMEDALLION.title", "Wolf Medallion");
        add("research.WOLFMEDALLION.stage.0", "A Medallion used by hunters in ages long past.<BR>Monster hunters.<BR>When its passive effect is on, I am periodically warned of the number of hostile monsters around me.<BR>When its active effect is used, I am able to temporarily see all nearby entities.<BR>And prepare for the worst.");

        add("research.BRONZESPHERE.title", "Bronze Sphere");
        add("research.BRONZESPHERE.stage.0", "I've already held this sphere.<BR>I know I have.<BR>My mind BURNS with recollection as I hold the object.<BR>I hope that reaching the Truth will unVeil this mistery.<BR>For now, it's again in my hands, and I can use it to my advantage.<BR>When activated it enables me to store experience inside it. If I am sneaking the experience is pulled out instead.<BR>A battery of Memories, so to say.");

        add("research.CRUCIBLE.title", "Crucible");
        add("research.CRUCIBLE.stage.0", "A weapon with a red hot blade, used by demon slayers in ages long past.<BR>One man armies they were, more terrible than any of their enemies.<BR>The Crucible is immensely powerful, capable of dishing out colossal damage for every hit.<BR>It is, however, balanced by the fact that for every swing the weapon needs around two minutes of recharging before it can be used again. And of course, many enemies of mine may not suffer too much damage in a single hit, so I must use it wisely.");

        add("research.BAUBLERESEARCH.title", "Baubles");
        add("research.BAUBLERESEARCH.stage.0", "Some items are to be worn outside of my armor slots.<BR>The Baubles I encounter in this journey (and only this journey) may have both active and passive effects.<BR>By holding the Bauble key ('Z' by default) I can choose which Baubles must have their passive effect enabled and which single Bauble is to be the active one.<BR>There can be only one active Bauble, and its effect can be activated by tapping the Bauble Key.");

        add("research.INTROSPECTIONDREAM.title", "Dreams of Introspection");
        add("research.INTROSPECTIONDREAM.stage.0", "A Memory of Introspection, as I reflect upon my own self while I walk among my worshippers.<BR>Through this Memory I can know more of the power I've obtained in my journey.<BR>Of course, were I to target someone else with the Dream I may also scry into their secrets.");

        add("research.DEEPROAR.title", "Roaring");
        add("research.DEEPROAR.stage.0", "While transformed I am unable to use the active effects of my Baubles, as well as to open the Bauble Powers menu.<BR>In that form, however, I am instead able to release a blood curdling roar that will affect all entities around me with Terror, by tapping the same key I'd have used for the Active Bauble.<BR>To strike fear in my opponents' hearts is to be closer than ever to God.");

        add("research.TONEWDEPTHS.title", "To New Depths");
        add("research.TONEWDEPTHS.stage.0", "We must keep going.<BR>Never yield, the Truth draws close, the Veil weakens, my knowledge grows, my will..<BR>What will is left? As if someone ripped my own spine.<BR>No, we'll keep going. We must.<BR>To the bottom of the deepest Oceans.<BR>To Arche, and then to Apeiron.<BR>Our journey won't end until we breach.. until we..<BR>..wait..<BR>..who's 'we'?");

        add("research.WATERDREAM.title", "Dreams of Water");
        add("research.WATERDREAM.stage.0", "A Dream of Water, as the thought of the Ocean refuses to leave my mind.<BR>Through this Memory I am able to locate the nearest Ocean Monument.<BR>The Guardians are born from water itself, and as such are closest to God.");

        add("research.DREAMFOCUS.title", "Dream Focus");
        add("research.DREAMFOCUS.stage.0", "Dream Foci are devices that, through their quartz structure and watery surface, can channel Dreams to move objects.<BR>Not my Dreams. Nor those of Weepers, who yet resist the final embrace.<BR>It is those of Fleti that I must harness, so lost in their desperate adoration of what lies Beyond.");
        add("research.DREAMFOCUS.stage.1", "A Dream Focus is a wondrous device.<BR>Its small end should be adjacent to the container I wish to pull items from. It can then be activated by placing a Fletum on its top.<BR>However, I must first choose the path the items will take, and I must do it myself.<BR>When no Fletum is on top of the Dream Focus, I can interact with the block with bare hands. I will then be transported to the large end of the Focus, facing away from it, and start to slowly move wherever I am looking, including in the air. This status lasts fifteen seconds, but I can exit it sooner by sneaking.<BR>Once all is done, the path is set. I can check the path at any time by wearing a Ring of Revelation with its passive effect enabled. I can toggle an individual path on and off by using a clay ball on its respective Focus. I may also apply dyes to the Dream Focus to change the path's color.<BR>With the path set, and a Fletum on top, the Focus will pull items from the container every seven and a half seconds. They will start to float in the air following the path I set, and drop on the path's end. All physics will still apply, and sharp turns around block edges may get the items stuck, so I must be careful when creating the path.<BR>The items can be picked up while in the air, by Players and hoppers alike. A careful combination of pathing and hoppers will result in a proper transportation system.<BR>The Focus can be turned off with a redstone signal.<BR>It also seems that floating swords and blackjacks retain their properties while flying through non Player creatures.<BR>Lastly, Villagers and Weepers in their item forms will be inserted in any Watery Cradle in their path.");
        add("research.crafteddream_focus.text", "Craft a Dream Focus.");

        add("research.CURTAINS.title", "Curtains");
        add("research.CURTAINS.stage.0", "Curtains are our world's counterpart to the Veil. Separating Truth from lies, behind them act a stageful of actors.<BR>Yes, maybe our reality is really nothing but a theatre.<BR>Still, these curtains I mean to make are a fair deal more mundane. They'll simply act as filters for the items that collide with them.");
        add("research.CURTAINS.stage.1", "Curtains act as filters for Dreams, just like the Veil. Some go through, some don't.<BR>In this case it's the Fleti's Dreams and the items they transport that may or may not pass.<BR>A Curtain must be placed under a container, such as a chest. Every time an item goes through, the Curtain checks whether the inventory above contains an instance of that item. If so, the item doesn't go through and stops floating, dropping instead to the ground or, preferably, to an aptly placed hopper.<BR>Having containers both above and below, with appropriate spacing between them to avoid all items being sucked in hoppers, makes for a fairly space-consuming setup, but one that can work well as a sorting system.");
        add("research.craftedcurtain.text", "Craft a Curtain.");

        add("research.DREAMFOCUSFLUIDS.title", "Dream Focus: Fluids");
        add("research.DREAMFOCUSFLUIDS.stage.0", "We should not stop at items! Fleti are more than capable of transporting other things.<BR>A Focus for Fluids would be a useful device.");
        add("research.DREAMFOCUSFLUIDS.stage.1", "Unlike its item counterpart, a Focus for Fluids plucks fluids from its top and ejects them from its bottom, which is where I'll be when starting to set the path.<BR>Fleti are to be placed on the device's sides, and up to four of them can be placed. Each one increases the amount of fluid transported at a time, in the order 100 -> 300 -> 600 -> 1000 millibuckets per go.<BR>Transported fluids will try to enter any tanks they come in contact with, and will disappear at the end of the path if no containers were found to store them.<BR>It is of note that Fleti that are \"fueling\" a Dream Focus of any kind will not prevent nearby Weepers or other Fleti from weeping into a Lacrymatory, thus making the Fluid Focus an appropriate method to empty Lacrymatories automatically.<BR>As a last note, floating water also appears to be a powerful fertiliser for nearby crops.");
        add("research.crafteddream_focus_fluids.text", "Craft a Dream Focus for fluids.");

        add("research.DREAMFOCUSVILLAGERS.title", "Dream Focus: Villagers");
        add("research.DREAMFOCUSVILLAGERS.stage.0", "My soon-to-be patients, whom I already stripped of all worldly things, seem content in knowing they have nothing more to lose.<BR>Those poor, poor things.<BR>I will take even the floor away from them.");
        add("research.DREAMFOCUSVILLAGERS.stage.1", "A Focus for Villagers is capable of moving Villagers in a path of my own choosing.<BR>The path begins from the block's top, by interacting with bare hands while no Fletum is activating the Focus. I will preserve my initial rotation after interacting with the block.<BR>Once the path is set, a Fletum must be placed below the Focus. Once that is done, the device will select every few seconds a Villager to move.<BR>The Villagers are taken from a 11x11 area centered on the Focus, up to 5 blocks above it. Crawling Villagers take precedence and are chosen before any standing ones. Child Villagers won't be taken, they must grow before reaching any usefulness.<BR>A redstone signal may be applied to the Focus to alter its functionality. A fully powered signal will disable the device altogether. A signal of strength between 1 and 14 instead determines how many adult standing Villagers will be left at all times in the area above.<BR>For example, with a signal of strength 3 and 2 standing Villagers above, none will be taken. This way their mating and reproduction is ensured.<BR>Crawling Villagers are exempt from this check, and will always be taken.<BR>If the Focus' path is aimed at a Watery Cradle, a floating Crawling Villager will automatically be placed in it if there are no patients already present. Of course, I must make sure to loop the path back to the Focus in case the Cradle wasn't empty.<BR>This is especially useful considering that a Blackjack floating through a Dream Focus for items will preserve its properties, and may even knockout Villagers while both item and victim are mid air.<BR>Of course, were I to fully automate surgery.. I'd need a Surgeon.");
        add("research.crafteddream_focus_villagers.text", "Craft a Dream Focus for Villagers.");

        add("research.SURGEON.title", "Surgeon");
        add("research.SURGEON.stage.0", "A net of Dreams links all those who peeked into the Truth.<BR>Entire societes exist away from me, some of these offering their services to allies and patrons.<BR>My alliance with Dagon brings new contacts, and among these come the Surgeons, offering their skills to those who seek them.<BR>I must craft a letter, a summons of sorts, for a Surgeon to reach me.<BR>I then only need use it on the ground. I'll do it once, then sort the Surgeon's job's details later, once I've taken a look at him.");
        add("research.SURGEON.stage.1", "I had wondered why Surgeons would ever form a society of their own.<BR>Little did I know that a Surgeon is not a profession.<BR>No, it is a race.<BR>One that does not belong to my world.<BR>Is this a race made with intent? After all, they all share a singular purpose.<BR>...<BR>What if I, too, were part of a breed that was *made*, not by chance but by will? Without knowing, for how I could I?<BR>A Surgeon is a useful servant, or rather, ally.<BR>I must place him close to a Watery Cradle with which I want him, her, it, whatever, to work.<BR>I must then use Surgery Tools on him to specify which action I want performed.<BR>The Surgeon will then operate every time a patient appears in the Cradle, placing the resulting patient on top of the device and keeping hearts or spines to himself. I can then interact with him with bare hands to be given all materials he gathered.<BR>A much more useful technique however is that of binding the Summons to a nearby chest before using it. To do that I can interact with a chest, wielding the letter, while sneaking.<BR>I can clear the previous binding by right clicking in the air while sneaking.<BR>A Surgeon summoned with a bound Summons will put any output of his operations, both patients and materials, in the bound chest, provided they're sufficiently close to each other.<BR>Since Villagers and Weepers in item form can enter Cradles as well, I can effectively pipeline Surgeons one after the other.<BR>A Surgeon can be picked up by its master with a blackjack.");
        add("research.summoned_surgeon.text", "Summon a Surgeon.");

        add("research.BLACKMIRROR.title", "Black Mirror");
        add("research.BLACKMIRROR.stage.0", "My mind is besieged. A tiny war with at most one casualty.<BR>Is there a Veil within myself?<BR>If a mirror reflects all that is me, then a Black Mirror that gives off no light will reflect nothing but my soul.<BR>And I should NOT peer into my soul.<BR>Spasms seized my writing hand, detaching what is thought from what is told. I must hold steadfast, and impress onto Al Azif only what I, and none other, believe.<BR>I must NOT look within myself. No, no, I MUST.");
        add("research.BLACKMIRROR.stage.1", "...<BR>How is this going to work?<BR>Like it always has. Write what's on your mind, and I'll nudge your hand. See how easy it is?<BR>I assure you, it just got a lot harder. I'll ask you not to interfere too much. Let's keep it a monologue.<BR>Alright.<BR>So, Arche. We need a Blood Well.");
        add("research.usedblack_mirror.text", "Do NOT use a Black Mirror.");

        add("research.FLESHCARBONTOKEN.title", "Token of Flesh and Carbon");
        add("research.FLESHCARBONTOKEN.stage.0", "So, let me guess, we'll be entering the Blood Well. Just like the dead.<BR>Indeed.<BR>Oh, what an omen.<BR>A Blood Well is connected to Arche itself. The red fluid makes for powerful binding, and the Well is a sort of capillary.<BR>One could see it like this: life and all that exists in our world is pushed upwards from Arche, through Void and bedrock, just like an artery pushes healthy blood to all organs.<BR>Yes, you know exactly what we'll be doing.<BR>We'll be going down the veins of our world. And, just like a vein brings back waste, or carbon dioxide, we will be carrying the detritus of our own world - that is, dead life, decomposing itself into purer carbon.");
        add("research.FLESHCARBONTOKEN.stage.1", "We're ready. Are you excited?<BR>Hopeful, more like. And, please, don't say 'You'. I can hardly tell who's writing what. Besides, shouldn't your voice be yellow?<BR>Ah, but this is *your* writing hand, just nudged here and there. My voice will always be down below, yellow as you see it, telling you what to craft and do.<BR>I still can't tell if I'm making you up.<BR>A tad offensive.<BR>Ah, let's go onwards.");
        add("research.craftedfleshcarbontoken.text", "Craft a Token of Flesh and Carbon.");

        add("research.ARCHE.title", "Arche");
        add("research.ARCHE.stage.0", "Arche plays by very different rules compared to our world. One would do best to learn them by heart.<BR>In the deepest oceans there will be very little air, banished to just a few structures. However, when we first get there we will indeed be in one such structure, and therefore get a breather of nice - if not fresh - air.<BR>Said structure will be fairly dark. Bring some torches. Ideally, we should go there in human form, to place those light sources, before transforming into a Deep One.<BR>At the center of the bottom floor of the structure there should be a blood portal back to the Overworld.<BR>Before entering the waters of Arche, you MUST transform. Don't even think about getting wet as a human - you will die. The pressure is too strong.<BR>As you well know, as a Deep One you may only hold maritime items. We won't be able to speak with each other through Al Azif or the Black Mirror. Additionally, your only two available food sources, slugs and fish, cannot be used in Arche. Slugs will swim away, fish restores no hunger. And hunger drains FAST in Arche.<BR>You must become part of the food chain. The creaturs of Arche are known as Ictya - they come in many shapes and sizes. Big fish hunts small fish, and nobody's safe when the big ones get hungry. Kill them to sate your belly.<BR>Reaching the Veil, even through Arche, is no small feat. We'd best grow more powerful. There are dwellings - nay, *cities* of Deep Ones down in Arche. They ask you to prove your worth first by hunting down Ictya, then by challenging their champions in their arenas.<BR>Here in Al Azif we'll record both the Ictyas we've slain and the skills we've acquired.<BR>It's time. Speak to me in the Mirror.");

        add("research.ICTYARY.title", "Ictyary");
        add("research.DOSKILLS.title", "Deep One Skills");


        add("ictya.sarfin.description", "The most populous inhabitants of Arche, sarfins are at the very bottom of the food chain, and serve as the primary food source for most larger Ictya, as well as you.<BR>They will never fight back, but are prone to fleeing when a larger creature gets too close. Due to their small size, hunting them can get both hard and frustrating. Try staying some small distance away from them, then quickly close in to attack before they have a chance to escape.");
        add("ictya.octid.description", "Easily the most annoying Ictya I've ever encountered, these little jerks are at the bottom of the food chain and never fight back against larger predators. However, as you may have already learned, they tend to flee when a possible predator gets too close, while also spraying pink ink right in their face. Unfortunately they do not distinguish between predator and regular passerby, and might blind you at most inopportune times. Perhaps in the Deep Cities we can find a solution.<BR>At least, the Octids make for a fine food source - stay some distance away from them, then close in quickly to attack before they have a chance to blind you.");
        add("ictya.muray.description", "Murays are found in the caves of Arche. They're small predators, and generally only attack the smallest of Ictya, though if hungry they may prey on creatures of their size.<BR>They don't pose a threat to us, though they will retaliate if attacked.<BR>Quite beautiful to look at when they slither through the waters of Arche. A bit less so when their three-sided jaws are open wide.");
        add("ictya.deep_angler.description", "Small predators found everywhere throughout Arche, Deep Anglers pose little threat to us, generally only preying on tiny Ictya.<BR>They follow an intriguing hunting pattern: when they're hungry and no prey is beside them, they will lure one from afar, then close in quickly once it gets near enough. One wonders whether we could learn from this.");
        add("ictya.dreadfish.description", "Dreadfishes are medium sized Ictya. This means that, while generally hunting for smaller prey, they may assail us as well if very hungry.<BR>They are fast and strong, and compared to most other Ictya they have a much closer bond between each other. Attacking one Dreadfish makes all its close by kin retaliate against the offender. This may be used to our advantage: if we get a large sized Ictya to accidentally attack a single Dreadfish, then those nearby will also join the fight.<BR>Just like crows, a large number of Dreadfishes is called a \"murder\", but for very different reasons.");
        add("ictya.man_o_war.description", "Contrary to popular belief, the Man O' War is not a jellyfish. It is, instead, a colony of small organisms that live together in harmony. This is most apparent when observing the Ictya's attack pattern: rather than chasing the enemy itself, it generates small creatures, each known as a \"Jelly\", which close in on the enemy to damage it, feeding the Man O' War when the target is slain.<BR>They are medium sized, and thus won't attack us unless very hungry.");
        add("ictya.umancala.description", "The Umancala's name derives from the words \"Umano\" and \"Calamaro\", respectively \"Human\" and \"Squid\". The squid part being obvious, the human attribute was given to it by its ancient discoverers when they mistook its flailing tentacles for a human swimmer's legs, used for propulsion in water.<BR>That's where the similarities end, however. The Umancala is a bizarre looking medium sized Ictya, generally preying on smaller Ictya and seldom attacking us, unless very hungry. They hunt by generating extremely hot balls of boiling water which are then fired towards targets, akin to a Blaze's fireballs in the Nether.");
        add("ictya.adeline.description", "The Adeline is a translucent medium sized Ictya, of little threat to us unless very hungry. Due to its transparency it can be hard for us to identify it, and it proves even harder for small Ictya, which won't flee even when it closes in on them.<BR>The Adeline got its name from its discoverer: Saint Adeline, wife of the legendary hero Saint Jerome with whom she ventured into Arche to hunt the Deep Ones.<BR>Rumour has it that it was actually Jerome who found it first, but that in his pride he didn't want his name attached to an oversized sea slug.");
        add("ictya.bonecage.description", "The Bonecage is an unsettling eel-like large Ictya. Due to its size, it might attack us when hungry.<BR>It's fast, strong and incredibly resilient. It attacks by opening up the boney spikes on its head, then violently closing them on the target as if to encase it in a veritable bone cage. Huge range and the tendency to pull targets in after hitting them means one would do best to avoid its attacks, and try circling it as much as possible to attack it from behind.");
        add("ictya.cephalopodian.description", "A large sized squid-like Ictya, the Cephalopodian is a fearsome creature which may be a threat to us when hungry.<BR>It attacks by closing its \"mouth-head\" on the unfortunate prey. If taken from behind it can also flail its tentacles dealing high knockback, but virtually no damage.<BR>Due to the high damage dealt by its mouth it is preferable to circle it and attack it from behind on its tentacles, though their flailing may prove itself a hindrance.<BR>It is rumoured that within the flaps of its mouth there is a pair of red and blue eyes, though no one has concrete proof of it as of yet.");
        add("ictya.sandflatter.description", "One of the most insidious creatures of Arche, the Sandflatter is a lobster-like large Ictya found in the more level regions of Arche. Unlike most other Ictya, it has a clear preference towards you Players, and will always be a threat when one gets unfortunately close.<BR>The small tentacle atop its back is merely a bait for unlucky fools, and if you don't escape the subsequent attack quickly you might not live to regret it.<BR>Once it has dislodged itself from its ambush position, it will chase the victim - if still alive - and rapidly whip down its health with its claws. Its sturdy exoskeleton provides resistance to damage to the Sandflatter unless it is attacked from below, on its softer belly - which is where you should always attack it.<BR>Yes, I should have told you that before you killed one. Apologies.");
        add("ictya.size.TINY", "Tiny");
        add("ictya.size.SMALL", "Small");
        add("ictya.size.MEDIUM", "Medium");
        add("ictya.size.LARGE", "Large");
        add("ictya.size.HUGE", "Huge");
        add("ictya.size.COLOSSAL", "Colossal");

        add("doskill.bosskill", "%1$s killed: %2$s");
        add("doskill.unlock.medium", "- A new medium sized Ictya must be slain to unlock this skill.");
        add("doskill.unlock.large", "- A new large sized Ictya must be slain to unlock this skill.");
        add("doskill.unlock.deep_one_brute", "- A Deep One Brute must be slain to unlock this skill.");
        add("doskill.unlock.deep_one_myrmidon", "- A Deep One Myrmidon must be slain to unlock this skill.");
        add("doskill.doquickstep.description", "When enabled, this skill lets you do quick sidesteps by pressing the Dodge Key ('Alt' by default). Like all skills, it requires you to be in your Deep One form. The direction of the dodge depends on the currently pressed movement keys (WASD).<BR>When used skillfully, the quickstep can speedily bring one out of danger, escaping perilous attacks. It may not, however, be used underwater or in mid air.");
        add("doskill.douppercut.description", "Whereas the quickstep puts distance between you and the enemy by moving you away from it, the uppercut achieves the same result by launching your adversary up in the air, with potentially fatal results in the subsequent landing. This skill, when toggled on, lets you right click on other creatures while barehanded to send them flying upwards.<BR>It does, however, become less effective against powerful enemies with large health pools, as well as bosses. It is also of little use in water combat.");
        add("doskill.doroarsink.description", "When enabled, the Sinking Roar empowers your normal roar by violently sending downwards any waterborne creatures affected by it. Additionally, if the creatures possess a limited air supply (like Players do) the roar will also knock all the air out of their lungs, making them immediately start drowning.<BR>Of course, several enemies are quite resistant to your roars: bosses, Ictya, and other creatures more fearsome than you may be entirely unaffected.");
        add("doskill.doclimbing.description", "This skill, when toggled on, enables you to jump upwards when adjacent to walls, effectively letting you easily scale vertical surfaces. When you are in mid air and near a solid block, you'll be able to jump off of it - this can be done repeatedly as long as you're near a scalable surface.<BR>Unfortunately, this skill does not work in the arena. There's no escaping the challenge.");
        add("doskill.dohealth.description", "A straightforward, passive ability, when enabled this skill simply grants extra health to your Deep One form.");
        add("doskill.dopoison.description", "When toggled on this skill gives you a chance to inject enemies with a powerful poison when you hit them. About one in four hits result in the effect being applied.<BR>It does not, unfortunately, work against bosses.");
        add("doskill.doregeneration.description", "When enabled, this skill gives you a regeneration effect every time you slay an enemy. The more powerful the felled creature, the greater the effect. Very weak creatures however might result in no effect being applied.");
        add("doskill.doquickstep.name", "Quickstep");
        add("doskill.douppercut.name", "Uppercut");
        add("doskill.doroarsink.name", "Sinking Roar");
        add("doskill.doclimbing.name", "Climbing");
        add("doskill.dohealth.name", "Extra Health");
        add("doskill.dopoison.name", "Poison Claws");
        add("doskill.doregeneration.name", "Corpse Feeding");
        add("doskill.toggled.on", "Enabled.");
        add("doskill.toggled.off", "Disabled.");
        add("doskill.unlock", "Unlock");
        add("doskill.toggle", "Toggle");

        add("deity.none", "None");
        add("deity.greatdreamer", "The Great Dreamer");

        add("worship.default", "Default");
        add("worship.penitence", "Penitence");
        add("worship.sacrifice", "Sacrifice");

        add("power.cooldown", "The Power requires %1$s more seconds of cooldown.");
        add("bauble.cooldown", "The Bauble requires %1$s more seconds of cooldown.");
        add("roar.cooldown", "The Roar requires %1$s more seconds of cooldown.");

        add("teleport.setposition", "My current position has been set as my return point.");
        add("teleport.inwater", "I must be in water to use this Power.");
        add("teleport.wrongdimension", "My last return point was in a different dimension. Using this Power again in the next 20 seconds will remove the return point.");
        add("teleport.removedpoint", "My return point has been reset.");

        add("beginning.netherreturn", "A new book lies in my hands, ready to be written. Al Azif I shall call it.");
        add("canoe.fishing.start", "A fish? From nowhere? Did it jump in or...          ...I should keep at this for a while.");
        add("canoe.fishing.end", "I think I've gained enough fish. I should eat enough slugs, then commune with the Idol.");

        add("arena.triumph", "§6TRIUMPH");
        add("arena.defeat", "§4DEFEAT");

        add("toast.ictyary", "New Ictyary Entry");
        add("toast.memory", "New Memory");

        add("sleep.transformed", "I cannot sleep while transformed.");
        add("sleep.parasite", "I don't feel all too well.. I can't sleep.");

        addResearch();
        addCraftingRegistry();
        addReminiscence();
        addGuis();
    }

    private void addResearch() {
        add("research.FIRSTDREAMS.title", "The Language of Dreams");
        add("research.didDream.text", "I must sleep. I must Dream.");
        add("research.crafted_metal.text", "Create a memory of metal.");
        add("research.reminisced_metal.text", "Find a way to talk to the dream.");
        add("research.FIRSTDREAMS.stage.0", "It feels natural to dismiss dreams as a trick of the mind, when such dismissal could itself be the trick.\\nThe sensation of travelling to and from the Nether felt familiar, and yet I have no recollection of any similar journey in my life. It left me with the intense feeling of plunging down, gods know how far into stone and bedrock, only to then rise right back up. A feeling I have often experienced in dreams.\\nCould I be underestimating their import? Are they born from my daily sensations, or could they tell me something §omore§r, about the world and about myself?\\nThe desire to understand swells within me. Dreams are such simple, everyday events whose true nature I never bothered to investigate, like a shallow-looking puddle whose bottom I never chose to touch.\\nBut what if it hid an ocean?");
        add("research.FIRSTDREAMS.stage.1", "It felt wrong. I woke up shuddering, drenched in sweat. It was like diving into a pool from thousands of man-heights above, head first, eyes closed.\\n" +
                "Yet I can make no conclusions. Was this.. §odream§r a simple reflection of my recent real life experiences, or did it have a life of its own?\\n" +
                "Oh, what a fascinating prospect! That of a sentient dream, living and breathing as I do. I wonder, then: could I talk to it? And if so, in what tongue?\\n" +
                "I propose to speak through my own §omemories§r: their effect on our dreams is undeniable. Most importantly, the study of human memories is a well-trodden field of research in academic circles. Past literature details the construction and usage of a memory sieve, a simple altar upon which I can place an item that evokes a certain memory, and subsequently store the result in a memory phial.\\n" +
                "I will begin with an iron ingot: an intense memory of metal, of the first time I mined a vein and saw wealth trickle in my hands. There is so much metal underground, could the dream see it?");
        add("research.FIRSTDREAMS.stage.2", "Replicating the steps of my predecessors was no issue. It is now my responsibility to tie it to the study of dreams.\\n" +
                "The goal is to have my own memory affect my mind while I sleep, and to do so I must experiment. A few different possibilities come to mind:\\n" +
                "[%s] Hold the phial in my hand while I sleep.\\1" +
                "[%s] Drink the phial's contents right before sleeping.\\1" +
                "[%s] (More complex) Build a fume spreader, place it next to my bed, fill it with oniric incense and then with the phial.\\n" +
                "Once I wake up, I must close my eyes and concentrate, to try and reminisce what the dream saw.");
        add("research.FIRSTDREAMS.stage.3", "It worked. \\nAn outlandish hypothesis, a wild shot in the dark and yet it §ostruck§r something, because there §owas§r something to strike in that gloom escaping our senses.\\nToday I pioneer the Art of Dreaming, so that humankind may see the world in a new light, and elude the limits of our worldly bodies. And I §ovow§r to continue exploring and expanding this field as far as I can, here on the pages of Al Azif.");

        add("research.SLEEP_CHAMBER.title", "Sleep Chamber");
        add("research.slept_in_chamber.text", "Experiment until you can answer at least the first question.");
        add("research.SLEEP_CHAMBER.stage.0", "I had always been proud of my ability to stay awake and work and walk as long as I needed, leaving sleep to times of absolute necessity. Now, however, it is sleep I need, and I cannot get enough of it. My research studies are limited by the frequency at which I can dream — a most frustrating bound.\\n" +
                "Thankfully, there is a great body of work aimed at replicating the effects of dreaming through various techniques and devices; one must only look at Carter's \"The Gate and the Key\" to find all sorts of examples.\\n" +
                "The method most easily within reach is through the use of a Sleep Chamber, i.e. a small room relying on water vapours to lull its tenant in a dreamlike state. One must simply open it, step inside and close the door. It is a somewhat complex contraption, and creating it will require a Gear Bench. \\n" +
                "Once the testbed is built, I may proceed with the evaluation. Building atop of past literature, in this study I aim to answer the following questions:\\n" +
                "1. Will the dreamlike state induced by the sleep chamber also undergo the effects of a nearby fume spreader?\\1" +
                "2. If so, how many times will I be able to achieve the effect in a single day (assuming there to be a limit)?\\1" +
                "3. And if such a limit does indeed exist, after reaching it will I still be able to dream in a normal bed at the end of the same day?");
        add("research.SLEEP_CHAMBER.stage.1", "The experiment with the fume spreader was successful, and the sleep chamber will likely prove itself an invaluable tool in my research.\\n" +
                "As soon as I have performed sufficiently many experiments, I may also answer my other questions:\\n" +
                "Max. uses of Sleep Chamber per day: ___\\1" +
                "May use bed after reaching Sleep Chamber limit: Yes/No.");
    }

    private void addCraftingRegistry() {
        add("research.CRAFTING.introduced", "First introduced in: {§2%1$s§r}[caption:%2$s].");

        add(FUME_SPREADER.get().getDescriptionId() + ".crafting", "Breathing its fumes while asleep will alter the behaviour of my dreams.\\1Must be placed on the ground near a bed or similar object. It must then be filled with {§noniric incense§r}[caption:crafting.oniric_incense] and, finally, with a {§nmemory phial§r}[caption:crafting.memory_phial] containing the desired memory.\\1It can be {§nemptied§r}[caption:Shift + Right Click], but doing so will not return the memory phial.");
        add(ONIRIC_INCENSE.get().getDescriptionId() + ".crafting", "Used on an empty {§nfume spreader§r}[caption:crafting.fume_spreader] to fill it.");
        add(MEMORY_PHIAL.get().getDescriptionId() + ".crafting", "A seminal discovery in the study of human memories. My contributions stand in its applications to dreams.\\1May be used on a {§nmemory sieve§r}[caption:crafting.memory_sieve] to capture a memory.");
        add(MEMORY_PHIAL.get().getDescriptionId() + ".crafting2", "A seminal discovery in the study of human memories. My contributions stand in its applications to dreams.\\1May be used on a {§nmemory sieve§r}[caption:crafting.memory_sieve] to capture a memory, and then on a full {§nfume spreader§r}[caption:crafting.fume_spreader] to assign it the contained memory.");
        add(MEMORY_SIEVE.get().getDescriptionId() + ".crafting", "A fundamental device in the art of manipulating memories.\\1An item {§nevoking a certain memory§r}[caption:memories] must be placed on it. Then, an {§nempty phial§r}[caption:crafting.memory_phial] may be used on it to capture the memory.");
        add(SLEEP_CHAMBER.get().getDescriptionId() + ".crafting", "A complex device known to mimic the dream state induced by sleeping.\\1To use it, I must open its door, walk inside and close it while enclosed within.");
    }

    private void addReminiscence() {
        add("reminiscence.EMPTY", "The dream was not affected.");
    }

    private void addGuis() {
        add("gui.sleep_chamber", "Sleep Chamber");
        add("gui.sleep_chamber.wake", "Leave chamber");
    }
}
