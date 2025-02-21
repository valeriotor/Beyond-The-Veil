package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.block.GearBenchBlock;
import com.valeriotor.beyondtheveil.lib.References;
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
        add(JOURNAL.get(), "Surgeon's Journal");
        add(GEAR.get(), "Gear");
        add(SURGEON_SUMMONS.get(), "Surgeon Summons");
        add(CRUCIBLE.get(), "Crucible");
        add(BLACK_MIRROR.get(), "Black Mirror");
        add(FLESH_CARBON_TOKEN.get(), "Token of Flesh and Carbon");
        add(PLUCKED_EYE.get(), "Plucked Eye");

        add("fluid_type.beyondtheveil.sedative_fluid", "Sedative");
        add("fluid_type.beyondtheveil.coagulant_fluid", "Coagulant");
        add("fluid_type.beyondtheveil.softener_fluid", "Softener");
        add("fluid_type.beyondtheveil.liquid_blaze_powder_fluid", "Serum X49B8 - Strength");


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

        addDialogue();
        addExchanges();
        addResearch();
        addCraftingRegistry();
        addReminiscence();
        addGuis();
        addSurgery();
        addCommonCaptions();
    }

    private void addExchanges() {
        add("correspondence.to", "To: ");
        add("correspondence.from", "From: ");
        add("correspondence.awaits", "§4Awaits reply");
        add("correspondence.redeem", "§4Redeem items");
        add("correspondence.shoreman_lighthouse_keeper", "Lighthouse Keeper");
        add("correspondence.shoreman_scholar", "The Scholar");
        add("correspondence.blood_cult", "???");
        add("correspondence.blood_cult_2", "???");
        add("correspondence.miskatonic_researchers_blood", "Miskatonic University");

        add("exchange.scholar_offer_help.object", "My knowledge is at your disposal\\n");
        add("exchange.scholar_offer_help.0.0.hello", "Dear Seeker,\\n");
        add("exchange.scholar_offer_help.0.1.this_is", "I am the Scholar of the shoremen, reaching you to express not only gratitude, but also great admiration: for your willingness to overcome those inner biases that gnaw at the mind of every human, for your desire to challenge your own beliefs in pursuit of what is true, and for your indomitable tenacity, unperturbed by the metanoia brought forth by communion.\\n");
        add("exchange.scholar_offer_help.0.2.feel_free", "I thus pledge myself to help in any way I can. I am knowledgeable about several topics, related both to our traditions here on the shore, and the wider academic circles I once belonged to. Do not hesitate to write or visit.\\n");
        add("exchange.scholar_offer_help.0.3.best", "Best,\\1The Scholar.");
        add("exchange.scholar_offer_help.1.0.hello", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.0.greetings", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.0.dear", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.1.dreamer", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.1.ocean", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.2.thanks", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.2.see_you", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.3.name", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.3.friend", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.3.seeker", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.1.3.traveller", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.2.0.hello", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.2.1.both", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.2.2.in_person", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.2.3.best", "My knowledge is at your disposal");

        add("exchange.keeper_ask_slugs.object", "A small request");
        add("exchange.keeper_ask_slugs.0.0.hello", "Hello,\\n");
        add("exchange.keeper_ask_slugs.0.0.greetings", "Greetings,\\n");
        add("exchange.keeper_ask_slugs.0.0.dear", "Dear Keeper,\\n");
        add("exchange.keeper_ask_slugs.0.1.customs", "I wish to become acquainted with the customs of the shoremen.\\n");
        add("exchange.keeper_ask_slugs.0.1.mission", "In my pursuit of knowledge, I seek to learn more about your kin.\\n");
        add("exchange.keeper_ask_slugs.0.2.slugs", "I had grown curious of the interest your brethren have towards the white slugs you hoard in your barrels. I would be grateful if you could send me a sample.\\n");
        add("exchange.keeper_ask_slugs.0.3.best", "Best,\\1");
        add("exchange.keeper_ask_slugs.0.3.regards", "Kind Regards,\\1");
        add("exchange.keeper_ask_slugs.0.4.name", "%s");
        add("exchange.keeper_ask_slugs.0.4.friend", "Your friend");
        add("exchange.keeper_ask_slugs.0.4.seeker", "The Seeker");
        add("exchange.keeper_ask_slugs.0.4.traveller", "The Traveller");
    }

    private void addCommonCaptions() {
        add("caption.right_click", "Right Click");
        add("caption.shift_right_click", "Sneak + Right Click");
        add("caption.crafting", "Go to Crafting registry");
        add("caption.journal", "Go to Journal entry");
    }

    private void addDialogue() {
        add("dialogue.continue", "(Continue)");
        add("dialogue.end", "(End)");

        addMirrorDialogue();
        addShoremanDialogue();
    }

    private void addMirrorDialogue() {

        add("dialogue.black_mirror.initial.start.0", "There is nothing here. A mirror so black I cannot even see myself.");
        add("dialogue.black_mirror.initial.end.option", "(End)");


        add("dialogue.black_mirror.initial2.must.0", "A tear is born of fear. From the past, present or future. And the shoremen fear what has happened and may happen once again.");
        add("dialogue.black_mirror.initial2.must.1", "The pain they endure is tremendous.. but why me? Why need I atone the sins of my forefathers?");
        add("dialogue.black_mirror.initial2.confirm.0", "Is this it?");
        add("dialogue.black_mirror.initial2.terrible.0", "A terrible thought to hold on to, yet hold on I must.");
        add("dialogue.black_mirror.initial2.terrible.1", "Have I erred? A grief so great — not of a person, but of a *people*. To accept a burden so heavy.. may shatter me.");
        add("dialogue.black_mirror.initial2.terrible.2", "Too late to turn back. My vision blurs, and a tear caresses my cheek. Such a little thing, yet of such incalculable weight.");
        add("dialogue.black_mirror.initial2.start.0", "There is just me, here. The small fortress that is my mind.");
        add("dialogue.black_mirror.initial2.weep.0", "What is it I fear most?");
        add("dialogue.black_mirror.initial2.death_.option", "Death.");
        add("dialogue.black_mirror.initial2.scrutiny.option", "Scrutiny by others.");
        add("dialogue.black_mirror.initial2.boredom_.option", "Boredom.");
        add("dialogue.black_mirror.initial2.the__.option", "The brittleness of life.");
        add("dialogue.black_mirror.initial2.that.option", "That I will never make a difference.");
        add("dialogue.black_mirror.initial2.losing.option", "Losing my family.");
        add("dialogue.black_mirror.initial2.pain_.option", "Pain.");
        add("dialogue.black_mirror.initial2.the_.option", "The ocean.");
        add("dialogue.black_mirror.initial2.the___.option", "The future.");
        add("dialogue.black_mirror.initial2.my.option", "My family.");
        add("dialogue.black_mirror.initial2.wasps_.option", "Wasps.");
        add("dialogue.black_mirror.initial2.sadness,.option", "Sadness, and the inability to explain it.");
        add("dialogue.black_mirror.initial2.open.option", "Open spaces.");
        add("dialogue.black_mirror.initial2.closed.option", "Closed spaces.");
        add("dialogue.black_mirror.initial2.spiders_.option", "Spiders.");
        add("dialogue.black_mirror.initial2.fear.option", "Fear itself.");
        add("dialogue.black_mirror.initial2.that_.option", "That humankind will never make a difference.");
        add("dialogue.black_mirror.initial2.the.option", "The dark.");
        add("dialogue.black_mirror.initial2.humiliation_.option", "Humiliation.");
        add("dialogue.black_mirror.initial2.weep.option", "Weep for the shoremen.");
        add("dialogue.black_mirror.initial2.grief_.option", "Grief.");
        add("dialogue.black_mirror.initial2.the____.option", "The present.");
        add("dialogue.black_mirror.initial2.snakes_.option", "Snakes.");
        add("dialogue.black_mirror.initial2.the_____.option", "The past.");
        add("dialogue.black_mirror.initial2.i.option", "I must weep.");
        add("dialogue.black_mirror.initial2.failure_.option", "Failure.");
        add("dialogue.black_mirror.initial2.germs_.option", "Germs.");
        add("dialogue.black_mirror.initial2.regret_.option", "Regret.");
        add("dialogue.black_mirror.initial2.loneliness_.option", "Loneliness.");
        add("dialogue.black_mirror.initial2.no,.option", "No, something else.");
        add("dialogue.black_mirror.initial2.lightning_.option", "Lightning.");
        add("dialogue.black_mirror.initial2.not.option", "Not now.");
        add("dialogue.black_mirror.initial2.not_.option", "Not being accepted.");
        add("dialogue.black_mirror.initial2.heights_.option", "Heights.");
        add("dialogue.black_mirror.initial2.yes_.option", "Yes.");

        add("dialogue.black_mirror.rationalize.insanity.0", "You have killed before. Creatures of all sorts. Life is not holy.");
        add("dialogue.black_mirror.rationalize.jerome.0", "Jerome was a warrior turned saint. Though tormented by regret, he still took lives – he knew it had to be done. For the greater good.");
        add("dialogue.black_mirror.rationalize.morons.0", "Massively influential morons, then. They shaped the modern cultural zeitgeist into one embracing nihilism, or worse, cosmicism. No one will decry the sacrifice of individual lives.");
        add("dialogue.black_mirror.rationalize.no_amount.0", "Oh, great start. Want to try again?");
        add("dialogue.black_mirror.rationalize.great_minds.0", "What do the great minds of history have to say?");
        add("dialogue.black_mirror.rationalize.need.0", "Animals die all the time. Horribly.");
        add("dialogue.black_mirror.rationalize.randolph.0", "Before his disappearance in recent years, Dr. Carter espoused his pessimistic views on the insignificance of human lives. To him, it would not have mattered.");
        add("dialogue.black_mirror.rationalize.adelina.0", "Adelina did not hide her belief in sacrifice for the greater good. Interestingly, she was said to have lived multiple lives, and that her final death was filled with regret.");
        add("dialogue.black_mirror.rationalize.no_hesitation.0", "Of course. Human lives are fleeting in the face of eternity, yet their sacrifice, in the noble pursuit of human knowledge, will be §oimmortal§r.");
        add("dialogue.black_mirror.rationalize.madman.0", "The mad desertman wrote much on the futility of life, even his own. He passed away by willingly imbibing poison.");
        add("dialogue.black_mirror.rationalize.start.0", "Let's begin.");
        add("dialogue.black_mirror.rationalize.humans.0", "Really? In the face of all that we have witnessed? Humans are not special. You think yourselves kings of the world and a step above your fellow animals, yet it is only the second step of a stairway that has no end.");
        add("dialogue.black_mirror.rationalize.humans.option", "Humans are more important.");
        add("dialogue.black_mirror.rationalize.in____.option", "In truth, I do not have a choice. But I will treat my patients with dignity, as their names enter the annals of history. (End)");
        add("dialogue.black_mirror.rationalize.out.option", "Out of need.");
        add("dialogue.black_mirror.rationalize.in__.option", "In truth, I do not have a choice. But I will treat my patients with dignity, as their names enter the annals of history. (End)");
        add("dialogue.black_mirror.rationalize.but.option", "But how would other humans see me, then? I would do this for the good of mankind, but they'd take me for a psychopath!");
        add("dialogue.black_mirror.rationalize.(the.option", "(The Madman of the Sands) \"I am a soft grain among dunes. Wind will sweep me away, as if I had never been.\"");
        add("dialogue.black_mirror.rationalize.i__.option", "I must still think about this. (End)");
        add("dialogue.black_mirror.rationalize.these.option", "These are morons.");
        add("dialogue.black_mirror.rationalize.i_____.option", "I must still think about this. (End)");
        add("dialogue.black_mirror.rationalize.(saint.option", "(Saint Adelina) \"A single life is inconsequential – unless its cessation changed the multitude it lied within. Then it becomes sacred, in its finality.\"");
        add("dialogue.black_mirror.rationalize.life's.option", "Life's sanctity is exalted and overstated by man's search for meaning, but the two are fundamentally incompatible. One must be ready for sacrifice.");
        add("dialogue.black_mirror.rationalize.i____.option", "I would be remembered not as a scientist, but as a murderer.");
        add("dialogue.black_mirror.rationalize.no.option", "No amount of rationalization, explanation and subsequent exculpation could ever justify life's desecration in the way that has been brewing in my cruel, wicked mind.");
        add("dialogue.black_mirror.rationalize.(dr_.option", "(Dr. Randolph Carter, of the Miskatonic) \"... and I opened the silvered gate, witnessing the unimaginable. We are nothing to infinity.\"");
        add("dialogue.black_mirror.rationalize.i_.option", "I must still think about this. (End)");
        add("dialogue.black_mirror.rationalize.(saint_.option", "(Saint Jerome) \"My sword weighs terribly, yet wield it I must. In choosing the lesser evil, I will do good – but I shall never claim heroism.\"");
        add("dialogue.black_mirror.rationalize.no_.option", "No. This is insanity.");
        add("dialogue.black_mirror.rationalize.i___.option", "I must still think about this. (End)");
        add("dialogue.black_mirror.rationalize.in.option", "In truth, I do not have a choice. But I will treat my patients with dignity, as their names enter the annals of history. (End)");
        add("dialogue.black_mirror.rationalize.in___.option", "In truth, I do not have a choice. But I will treat my patients with dignity, as their names enter the annals of history. (End)");
        add("dialogue.black_mirror.rationalize.life's_.option", "Life's sanctity is exalted and overstated by man's search for meaning, but the two are fundamentally incompatible. One must be ready for sacrifice.");
        add("dialogue.black_mirror.rationalize.in_.option", "In truth, I do not have a choice. But I will treat my patients with dignity, as their names enter the annals of history. (End)");
        add("dialogue.black_mirror.rationalize.i.option", "I must still think about this. (End)");
        add("dialogue.black_mirror.rationalize.but_.option", "But how can one live with hands stained in blood?");




    }
    private void addShoremanDialogue() {
        addShoremanLighthouseKeeperDialogue();

        add("dialogue.shoreman_fisherman.initial.start.0", "Huh? [[Who are you?");
        add("dialogue.shoreman_fisherman.initial.traveller.option", "A traveller.");
        add("dialogue.shoreman_fisherman.initial.seeker.option", "A seeker of truth.");
        add("dialogue.shoreman_fisherman.initial.leave.0", "||Leave.");
        add("dialogue.shoreman_fisherman.initial.alright.option", "Alright. Farewell.");
        add("dialogue.shoreman_fisherman.initial.no_way.option", "This is no way to treat a weary traveller!");
        add("dialogue.shoreman_fisherman.initial.begone.0", "]I said begone![ Your kind are §onot§r welcome here!");
        add("dialogue.shoreman_fisherman.initial.prejudice.option", "Your prejudice will be your downfall.");
        add("dialogue.shoreman_fisherman.initial.my_kind.option", "§oMy§r kind?! Different we may look, we are both fellow humans!");
        add("dialogue.shoreman_fisherman.initial.sundered.0", "We have nothing in common. Whatever bond you may think we share: there is none, and if there had ever been one then it was violently sundered.");
        add("dialogue.shoreman_fisherman.initial.same_flesh.option", "We are both creatures of flesh and blood. Does that not unite us?");
        add("dialogue.shoreman_fisherman.initial.lol_no.0", "No.||\nWe are nothing alike.");
        add("dialogue.shoreman_fisherman.initial.for_the_best.option", "And perhaps for the best.");
        add("dialogue.shoreman_fisherman.initial.hospitality.option", "So much for hospitality!");

        add("dialogue.shoreman_fisherman.no_closer.start.0", "No, no, no! Don't get close, we suffered too much already!");
        add("dialogue.shoreman_fisherman.no_closer.no_harm.option", "I swear not to harm you.");
        add("dialogue.shoreman_fisherman.no_closer.who_hurt.option", "Who made you suffer?");
        add("dialogue.shoreman_fisherman.no_closer.no_believe.0", "]]I don't believe you! I don't believe you!\nAway! Away!");
        add("dialogue.shoreman_fisherman.no_closer.forget.option", "Oh, forget it.");
        add("dialogue.shoreman_fisherman.no_closer.farewell.option", "Farewell.");
        add("dialogue.shoreman_fisherman.no_closer.you_did.0", "You did. Do you not remember?");
        add("dialogue.shoreman_fisherman.no_closer.never.option", "I never did. I never §owould§r have.");
        add("dialogue.shoreman_fisherman.no_closer.trick.option", "Is this a trick?");
        add("dialogue.shoreman_fisherman.no_closer.me_or_us.option", "'You' as in §ome§r? Or §ous§r?");
        add("dialogue.shoreman_fisherman.no_closer.rubbish.option", "You talk rubbish.");
        add("dialogue.shoreman_fisherman.no_closer.upon_deaths.0", "Deaths upon deaths upon deaths. \nAnd now you have forgotten.");
        add("dialogue.shoreman_fisherman.no_closer.idiot.option", "I get it. You are the village idiot.");
        add("dialogue.shoreman_fisherman.no_closer.recorded.option", "I do not forget. All my deeds are recorded in §oAl Azif§r.");

        add("dialogue.shoreman_fisherman.begone.start.0", "Begone!");
        add("dialogue.shoreman_fisherman.begone.farewell.option", "Farewell.");
        add("dialogue.shoreman_fisherman.begone.tone.option", "Watch. Your. Tone.");
        add("dialogue.shoreman_fisherman.begone.soon.option", "I will most certainly soon be gone.");


        //add("dialogue.shoreman_scholar.initial.start.0", "Oh? Good day, dear pilgrim. Are you in search of something, here in our humble dwelling?");
        //add("dialogue.shoreman_scholar.initial.knowledge.option", "Knowledge.");
        //add("dialogue.shoreman_scholar.initial.brought.option", "No. I was brought here by a dream.");
        //add("dialogue.shoreman_scholar.initial.suspicion.option", "Someone who does not view me with the stern eyes of suspicion.");
        //add("dialogue.shoreman_scholar.initial.start.0", "Oh? Good day, dear pilgrim. Are you in search of something, here in our humble dwelling?");

        add("dialogue.shoreman_scholar.initial.start.0", "Oh? Good day, dear pilgrim. Are you in search of something, here in our humble dwelling?");
        add("dialogue.shoreman_scholar.initial.knowledge.0", "Ah, I understand.\nIndeed, I spy in your eyes that burning flame of curiosity, searing idleness away and leading you into that incessant march of learning.");
        add("dialogue.shoreman_scholar.initial.knowledge.1", "I can well grasp your struggle. I, too, am a scholar, and oh the wonders I have seen and dreamt of!");
        add("dialogue.shoreman_scholar.initial.dream.0", "A dream? Of our black shores and dark water?\nI see. Perhaps, then, our dreams met already. Deep down below, at the bottom of the ocean.");
        add("dialogue.shoreman_scholar.initial.alas.0", "Alas, our customs dictate I do not discuss my learnings with outsiders.\nI am sorry.");
        add("dialogue.shoreman_scholar.initial.prejudice.0", "Prejudice? I.. I...\n..I see. You have given me food for thought, traveller.");
        add("dialogue.shoreman_scholar.initial.suspicion.0", "I see. Please understand that it is not a mere lack of trust. In view of an outsider, our hearts are gripped with fear. By instinct, almost.\nYet it is not innate. There is much history behind our demeanor.\nMuch fury. Much grief. Much regret.");
        add("dialogue.shoreman_scholar.initial.just.option", "Just like the others, blinded by prejudice.");
        add("dialogue.shoreman_scholar.initial.knowledge_.option", "Knowledge.");
        add("dialogue.shoreman_scholar.initial.no_.option", "No. I was brought here by a dream.");
        add("dialogue.shoreman_scholar.initial.___.option", "...");
        add("dialogue.shoreman_scholar.initial.i.option", "I understand.");
        add("dialogue.shoreman_scholar.initial.continue_.option", "(Continue)");
        add("dialogue.shoreman_scholar.initial.wait,.option", "Wait, you know about dreams?");
        add("dialogue.shoreman_scholar.initial.farewell.option", "Farewell.");
        add("dialogue.shoreman_scholar.initial.continue.option", "(Continue)");
        add("dialogue.shoreman_scholar.initial.someone.option", "Someone who does not view me with the stern eyes of suspicion.");

        add("dialogue.shoreman_scholar.initial1.start.0", "Alas, our customs dictate I do not discuss my learnings with outsiders.\nI am sorry.");
        add("dialogue.shoreman_scholar.initial1.i.option", "I understand");
        add("dialogue.shoreman_scholar.initial1.___.option", "...");


        add("dialogue.shoreman_clerk.initial.talk.0", "What's there to talk about? You and I are too different, only seemingly speaking the same language, yet in truth our words are incomprehensible to one another.");
        add("dialogue.shoreman_clerk.initial.start.0", "Huh? Are you here to buy or to steal?");
        add("dialogue.shoreman_clerk.initial.obvious.0", "No.");
        add("dialogue.shoreman_clerk.initial.steal.0", "Of course, just arrived and yet you already plunder our resources, stealing from our chests and barrels our hard earned goods.\nBecause you just §odo not care§r about ones such as us.");
        add("dialogue.shoreman_clerk.initial.steal.1", "That's the truth of it. To you, we are irrelevant. Insignificant. And, given the opportunity, you would wipe us all out| — not out of need, but just because you §ocould§r.");
        add("dialogue.shoreman_clerk.initial.buy.0", "Hmph. Special prices, just for you.");
        add("dialogue.shoreman_clerk.initial.refrain.0", "I know.\n§oI know.§r");
        add("dialogue.shoreman_clerk.initial.anyone.0", "Hmm.|\nTry the lighthouse keeper. He sees the clearest, up there in his tower.");
        add("dialogue.shoreman_clerk.initial.and.option", "And I may do it again.");
        add("dialogue.shoreman_clerk.initial.farewell__.option", "Farewell.");
        add("dialogue.shoreman_clerk.initial.i.option", "I was joking! Was it not obvious?!");
        add("dialogue.shoreman_clerk.initial.ehm___.option", "Ehm.. still up to trade?");
        add("dialogue.shoreman_clerk.initial.thanks_.option", "Thanks. Farewell.");
        add("dialogue.shoreman_clerk.initial.alright_.option", "Alright. Farewell.");
        add("dialogue.shoreman_clerk.initial.let's.option", "Let's see.");
        add("dialogue.shoreman_clerk.initial.farewell_.option", "Farewell.");
        add("dialogue.shoreman_clerk.initial.(nod).option", "(Nod)");
        add("dialogue.shoreman_clerk.initial.ehm__.option", "Ehm.. still up to trade?");
        add("dialogue.shoreman_clerk.initial.buy_.option", "Buy.");
        add("dialogue.shoreman_clerk.initial.not.option", "Not now.");
        add("dialogue.shoreman_clerk.initial.i_.option", "I do not deny that, in the past, I have not refrained from killing.");
        add("dialogue.shoreman_clerk.initial.neither_.option", "Neither. Just talk.");
        add("dialogue.shoreman_clerk.initial.is.option", "Is there anyone willing to speak to me?");
        add("dialogue.shoreman_clerk.initial.steal_.option", "Steal.");

        add("dialogue.shoreman_clerk.initial1.start.0", "Hmph. Special prices, just for you.");
        add("dialogue.shoreman_clerk.initial1.not.option", "Not now.");
        add("dialogue.shoreman_clerk.initial1.let's.option", "Let's see.");



        add("dialogue.shoreman_carpenter.initial.oldest.0", "Father Ocean spawned Daughter Earth, and not the other way around.\nYour beliefs are apocryphal.");
        add("dialogue.shoreman_carpenter.initial.unknowable.0", "Ah, then you believe in the Unknowable! And so do we, deeper even than the ocean.");
        add("dialogue.shoreman_carpenter.initial.start.0", "Hmph.");
        add("dialogue.shoreman_carpenter.initial.common.0", "No. Our closest common ancestor is water, that most ancient of substances that gave birth to all things, predating and fathering blood.");
        add("dialogue.shoreman_carpenter.initial.hmph.0", "Go. Return whence you came.\nWe have §onothing§r in common.");
        add("dialogue.shoreman_carpenter.initial.siblings.0", "If you seek your siblings, go back to your people. We are not brethren. Hah! Not even cousins!");
        add("dialogue.shoreman_carpenter.initial.hmph.option", "Hmph to you too.");
        add("dialogue.shoreman_carpenter.initial.why.option", "Why are you folks so cold! Are we not all brothers and sisters?");
        add("dialogue.shoreman_carpenter.initial.i.option", "I do not \"believe\". I §oknow§r.");
        add("dialogue.shoreman_carpenter.initial.nothing?.option", "Nothing? We are both humans, born of the same blood, the same ancestors!");
        add("dialogue.shoreman_carpenter.initial.very.option", "Very well. Goodbye.");
        add("dialogue.shoreman_carpenter.initial.but.option", "But are we not born of the same common ancestor? We both share its blood.");
        add("dialogue.shoreman_carpenter.initial.yes_.option", "Yes. §oThere§r is something we have in common.");
        add("dialogue.shoreman_carpenter.initial.water.option", "Water is not the oldest substance. Not even close.");
        add("dialogue.shoreman_carpenter.initial.i_.option", "I do not \"believe\". I §oknow§r that I §ocannot know§r.");
        add("dialogue.shoreman_carpenter.initial.whatever.option", "Whatever you say.");

        add("dialogue.shoreman_carpenter.initial1.start.0", "I am a woodworker, build canoes for the fishermen. But my craft is not for you outsiders.");
        add("dialogue.shoreman_carpenter.initial1.your.option", "Your woodwork is out and about, what's stopping me from taking it?");
        add("dialogue.shoreman_carpenter.initial1.i.option", "I have not interest in your damp, creaking lumber.");
        add("dialogue.shoreman_carpenter.initial1.it.option", "It is a shame. Your art would be highly valued among my people.");



        add("dialogue.shoreman_bartender.initial.start.0", "Hmph. We do not take kindly to outsiders, but I won't deny you a night's rest.\nThere are beds up above.");
        add("dialogue.shoreman_bartender.initial.anything.option", "Anything for sale?");
        add("dialogue.shoreman_bartender.initial.thank.option", "Thank you.");

        add("dialogue.shoreman_drunk.initial.mean.0", "It's just.. I dunnae like life here no more. Ne'er did, actually.\nBroodin', borin' fishermen. Misbegotten children of earth and water, I tell ya! That's what we be!");
        add("dialogue.shoreman_drunk.initial.start.0", "A newcomer, eh? We dun't see many outsiders no'adays.\nGood for ya. Being an outsider an' all.");
        add("dialogue.shoreman_drunk.initial.brethren.0", "You'd talk ill too, had ya seen what I seen.");
        add("dialogue.shoreman_drunk.initial.elaborate.0", "]]Hmmm.[[\nNah. Cannae talk on a parched throat.");
        add("dialogue.shoreman_drunk.initial.sober.0", "Huh? Sauber? Wut's that?");
        add("dialogue.shoreman_drunk.initial.what.option", "What do you mean?");
        add("dialogue.shoreman_drunk.initial.thanks_.option", "Thanks. You look very far from sober, by the way.");
        add("dialogue.shoreman_drunk.initial.nothing_.option", "Nothing. I wonder why you'd count me lucky as an outsider?");
        add("dialogue.shoreman_drunk.initial.please,.option", "Please, do elaborate.");
        add("dialogue.shoreman_drunk.initial.i.option", "I see. Good day, then.");
        add("dialogue.shoreman_drunk.initial.drunkard__.option", "Drunkard..");
        add("dialogue.shoreman_drunk.initial.nevermind_.option", "Nevermind. Farewell.");
        add("dialogue.shoreman_drunk.initial.go.option", "Go on..");
        add("dialogue.shoreman_drunk.initial.how.option", "How can you talk so ill of your brethren?");

        add("dialogue.shoreman_drunk.initial1.start.0", "Ach, cannae drink sea water. Ne'er again...");
        add("dialogue.shoreman_drunk.initial1.can't.option", "Can't get anything out of a thirsty man.");
        add("dialogue.shoreman_drunk.initial1.might.option", "Might be good if you don't drink for a while..");

        add("dialogue.shoreman_drunk.drunk.secluded.0", "Nono, we ain't secluded. We samply hate y'all, thar's all.\nCause y'all hate us, I guess. Dunno.");
        add("dialogue.shoreman_drunk.drunk.where.0", "Ain't seen 'im in a while. Swimmin' somewhere. Deep down below.");
        add("dialogue.shoreman_drunk.drunk.kin.0", "Yah but then why ye gotta kill me ma, an' then ye try an' kill me pa, but then me pa kill yer pa, an' wait where was I goin' with this...");
        add("dialogue.shoreman_drunk.drunk.enough.0", "Ooof, why can't ya stooop. Ye go on an' on an' on. Yah thar's why we hate ye. Not cause ye killed me ma. Ye just ask too much.");
        add("dialogue.shoreman_drunk.drunk.saying.0", "Oh yah. What was I sayin'?||\nAh, may'aps somethin' bout us fisherfolk. Yah we a nasty bunch. Pale all over.| Except me nose.");
        add("dialogue.shoreman_drunk.drunk.pa.0", "Oh betcha he was a good looking fella. Not like mine. Slimy and all.");
        add("dialogue.shoreman_drunk.drunk.unlike.0", "Oi! It's ye who look weird an' all. Thar's why we hate y'all. Ye just cannae stop hatin' §ous§r.");
        add("dialogue.shoreman_drunk.drunk.start.0", "Ain't thar the good stuf-|| §o*hic*§r beg pardon.");
        add("dialogue.shoreman_drunk.drunk.i_.option", "I don't hate you, and I'm sure my kin wouldn't either.");
        add("dialogue.shoreman_drunk.drunk.i__.option", "I only hate you because you hate §ome§r.");
        add("dialogue.shoreman_drunk.drunk.i____.option", "I only hate you because you hate §ome§r.");
        add("dialogue.shoreman_drunk.drunk.why.option", "Why are you folks so secluded?");
        add("dialogue.shoreman_drunk.drunk.i___.option", "I don't hate you, and I'm sure my kin wouldn't either.");
        add("dialogue.shoreman_drunk.drunk.i.option", "I don't even know \"me pa\"..");
        add("dialogue.shoreman_drunk.drunk.not.option", "Not to sound rude, but why do you all look so unlike the.. other humans of the world?");
        add("dialogue.shoreman_drunk.drunk.you're.option", "You're calling your dad... slimy?");
        add("dialogue.shoreman_drunk.drunk.deep.option", "Deep down below where??");
        add("dialogue.shoreman_drunk.drunk.serves.option", "Serves you being a drunkard.");
        add("dialogue.shoreman_drunk.drunk.where's.option", "Where's your \"pa\" right now?");
        add("dialogue.shoreman_drunk.drunk.you.option", "You were saying..");
        add("dialogue.shoreman_drunk.drunk.sorry__.option", "Sorry.. I see you're getting tired.");

        add("dialogue.shoreman_drunk.drunk1.start.0", "]]Ye just go on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' on an' §o*hic*§r on an' on an' on an' on an' on an' on!!");
        add("dialogue.shoreman_drunk.drunk1.ok__.option", "Ok..");



    }

    private void addShoremanLighthouseKeeperDialogue() {
        add("dialogue.shoreman_lighthouse_keeper.initial.start.0", "Oh. A traveller. \nWelcome.");
        add("dialogue.shoreman_lighthouse_keeper.initial.start.1", "How did you stumble upon our hamlet, may I ask? Was it chance? Or did you seek us?");
        add("dialogue.shoreman_lighthouse_keeper.initial.mere_chance.option", "Mere chance.");
        add("dialogue.shoreman_lighthouse_keeper.initial.hard_to_believe.option", "As hard as it is to believe, I was guided here by a dream.");
        add("dialogue.shoreman_lighthouse_keeper.initial.dream.0", "A.. dream? A dream of what?");
        add("dialogue.shoreman_lighthouse_keeper.initial.darkness.option", "Darkness.");
        add("dialogue.shoreman_lighthouse_keeper.initial.guess_chance.option", "I didn't know it would lead me here, so I guess it was chance.");
        add("dialogue.shoreman_lighthouse_keeper.initial.precursor.0", "The precursor of light. Rising up from the ocean to blind the eyes of man.\nBut no, do not worry. Here, atop the lighthouse, you can see §oeverything§r.");
        add("dialogue.shoreman_lighthouse_keeper.initial.precursor.1", "Tell me then, traveller. What revelations do you hope to uncover?");
        add("dialogue.shoreman_lighthouse_keeper.initial.still_exploring.option", "I do not know. I am still exploring, the world and myself.");
        add("dialogue.shoreman_lighthouse_keeper.initial.so_much.0", "I see. There has to be so much you must have seen in your endless journeys. I can only envy you, stuck as I am, tending this lighthouse.");
        add("dialogue.shoreman_lighthouse_keeper.initial.so_much.1", "Yet, I am left to wonder, what does an explorer like you think of our humble hamlet? Out of all the beauties you must have admired, what is it that strikes you most?");
        add("dialogue.shoreman_lighthouse_keeper.initial.unique_architecture.option", "The unique architecture, how everything fits upon the water on which it's built.");
        add("dialogue.shoreman_lighthouse_keeper.initial.idol.option", "That idol in the town square.");
        add("dialogue.shoreman_lighthouse_keeper.initial.asunder.0", "Ahh, I see. Truly, we are so very proud of what we have built. \nAnd rebuilt. \nAnd rebuilt over and over again, just for it to be set ablaze, and rent asunder.");
        add("dialogue.shoreman_lighthouse_keeper.initial.asunder.1", "Know you not of what and whom I speak? Of the misdeeds of ages past? Crimes and atrocities, committed against us by self proclaimed 'saints' and 'hunters', blinded by their fear and prejudice.\nThey were your ancestors, traveller. And just like them, you are not welcome here.");
        add("dialogue.shoreman_lighthouse_keeper.initial.him.0", "Him? Yes, we chose to portray him in a most striking visage. Amorphous, almost shapeless. Inscrutable.\nAnd, indeed, striking he was, inviting the most violent and ruinous attention.");
        add("dialogue.shoreman_lighthouse_keeper.initial.him.1", "Know you not of what and whom I speak? Of the misdeeds of ages past? Crimes and atrocities, committed against us by self proclaimed 'saints' and 'hunters', blinded by their fear and prejudice.\nThey were your ancestors, traveller. And just like them, you are not welcome here.");
        add("dialogue.shoreman_lighthouse_keeper.initial.not_aware.option", "I.. was not aware. Our church speaks of \"sinners\", and of \"inhuman eretics\". I did not imagine..");
        add("dialogue.shoreman_lighthouse_keeper.initial.just_humans.0", "We are just §ohumans§r!  But §o§lyou§r were fearful of us venerating a different god, and all that he represents.\nBecause §owe§r worship the Veil, that great final threshold all dreams go through. The clouding of thoughts, the fog in the night, the blindness of man and the prejudice driving you towards unspeakable acts.");
        add("dialogue.shoreman_lighthouse_keeper.initial.just_humans.1", "And it lives deep down below, at the bottom of the sea. Hiding all sorts of truths, that filter of infinity in the emptiness of the §ovoid§r.\nYour ancestors could not tolerate its existence, because it would have shattered all your beliefs.");
        add("dialogue.shoreman_lighthouse_keeper.initial.not_forefathers.option", "I am not my forefathers.");
        add("dialogue.shoreman_lighthouse_keeper.initial.beg.0", "Then §oplease§r, I beg of you..\n§oLet no further harm come upon my people.§r");
        add("dialogue.shoreman_lighthouse_keeper.initial.no_fear.option", "You must not fear me.");
        add("dialogue.shoreman_lighthouse_keeper.initial.mankind.option", "My mission is for all of mankind, and that includes §oyou§r.");
        add("dialogue.shoreman_lighthouse_keeper.initial.groundless.option", "Your accusations are groundless. There are no records of any such \"crusades\". I cannot trust your words.");

        add("dialogue.shoreman_lighthouse_keeper.initial1.start.0", "§oPlease§r.. I beg of you..\n§oLet no further harm come upon my people.§r");
        add("dialogue.shoreman_lighthouse_keeper.initial1.i.option", "I cannot trust you.");
        add("dialogue.shoreman_lighthouse_keeper.initial1.do.option", "Do not fear.");

        add("dialogue.shoreman_lighthouse_keeper.did_dream.afraid.0", "We are so, so terribly afraid, traveller. Of the past repeating itself. Can you blame us? When staring in the eyes of genocide, how could one respond?");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.afraid.1", "Your ancestors were frightened by our customs, of the black soil that spread beneath our hamlets. They were afraid of our worship and beliefs, terrorized by a deity truer than theirs.\nBut, perhaps, you may yet break the cycle of agony. Were you to learn,| rather than kill. To be curious,| rather than prejudiced.");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.unlike.0", "We are frightened, traveller, and we weep, for tears are born of fear.\nWill you weep for us?");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.start.0", "I saw you tonight, traveller. Or rather, in my dream I saw yours. Speeding down below, towards the bottom of the sea.\nIt was a brave, angry dream, seeking an unveiling of truth as if dissatisfied with what you already knew.");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.start.1", "When we first met, I spoke of fear,| and of prejudice,| yet chastised you for your heritage. I ask you now to forgive my hypocrisy.");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.why.option", "Why did you fear me so?");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.i.option", "I will, because it's right.");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.i__.option", "I forgive you.");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.a.option", "A bond to reconcile ancient enmities. I shall.");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.i_.option", "I will, because I seek Truth.");
        add("dialogue.shoreman_lighthouse_keeper.did_dream.to.option", "Embracing all of your grief... I don't know if I can.");

        add("dialogue.shoreman_lighthouse_keeper.did_dream2.start.0", "Will you shed a tear for our sake?");
        add("dialogue.shoreman_lighthouse_keeper.did_dream2.end.option", "(End)");

        add("dialogue.shoreman_lighthouse_keeper.thank_you.admire.0", "I admire your resolve. You do not flee the Veil, when you dream nor when you weep. You wish to breach it, go beyond the lies you are told and grown into.");
        add("dialogue.shoreman_lighthouse_keeper.thank_you.admire.1", "Look at the ocean, traveller. The boundless bulwark concealing truth. The Great Dreamer lies beneath it, the last door before the unknown.\nSeek communion, down at the idol in the town center, and adopt our beliefs. Acknowledge the ignorance the human mind is born into| – only then can you overcome it. Farewell, seeker.");
        add("dialogue.shoreman_lighthouse_keeper.thank_you.start.0", "Thank you, traveller. Yours is a rare gift. To us, and to yourself.");
        add("dialogue.shoreman_lighthouse_keeper.thank_you.may.option", "May we meet again.");
        add("dialogue.shoreman_lighthouse_keeper.thank_you.the.option", "The old truths were deceits. How can I dispel the dogmas I grew up believing in?");

        add("dialogue.shoreman_lighthouse_keeper.thank_you2.start.0", "Good luck, friend.");
        add("dialogue.shoreman_lighthouse_keeper.thank_you2.end.option", "(End)");


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

        add("research.SENTIENCE.title", "Sentient Dreams of Sentience");
        add("research.reminisced_sentience.text", "Dream of Sentience.");
        add("research.found_sentience.text", "See where the dream leads me.");
        add("research.SENTIENCE.stage.0", "Discovering and acknowledging the intelligence of dreams has marked me with a wild, irrepressible memory, the realization upheaving my view of the world. I spoke to it in the language of thoughts, and it replied in kind as I reminisced.\\n" +
                "Yet, I am not the only dreamer — indeed, there is hardly a thing more characteristic of sentient creatures than the ability to dream. What could then be happening in their own, weird plane of existence, in however few or many its dimensions and laws? Do dreams talk to one another? And if so, is it a loud and violent cacophony of disparate and disparaging memories or a harmonious exchange among all those beings blessed and united by the gift of life?\\n" +
                "When I dream of a loved one, is it a gentle caress or forceful poke?\\n" +
                "I must talk of sentience to the dream, and see if it too knows such a memory.");
        add("research.SENTIENCE.stage.1", "Reminiscing brought me to a village, showing that dreams do indeed know and see each other.\\n" +
                "This could introduce a fascinating subfield. Think of all the ways we would employ our dreams, making them our envoys and interpreters to speak with all other beings of the world (the universe?). Friends and allies of all miens and shapes, brought together within the realm of thoughts.\\n" +
                "But what about enemies? Can villains make their dreams hostile, fueled by disdain rather than solidarity?\\n" +
                "And what if there were dreamers much greater and more skillful than me? Of all shapes, or lack thereof.\\n" +
                "Perhaps to dream is to put myself at risk. Of my mind being hijacked. And no longer my own.");

        add("research.DARKNESS.title", "Before There Was Light");
        add("research.reminisced_darkness.text", "Dream of Darkness.");
        add("research.found_darkness.text", "See where the dream leads me.");
        add("research.spoke_dweller.text", "Speak to the inhabitants.");
        add("research.unlocked_hamlet.text", "Speak to the keeper of the lighthouse.");
        add("research.DARKNESS.stage.0", "Dreams dance unseen. Escaping all senses, they are destined to be inscrutable — until we shut our eyes. \\n" +
                "(Can closing one curtain open another?)\\n" +
                "In reminiscing, my sight was gone, and yet my mind saw clearly: a new memory flared, bright and blinding. \\n" +
                "A memory of darkness. That nightly veil obscuring the senses. Pitch black, predating light itself (but how could any creature remember so far back in time! Is it instilled in our very being?)\\n" +
                "I must follow it, but cannot imagine where it will lead me.\\n" +
                "Will I finally see clear? \\n" +
                "Or will I be blind for evermore?");
        add("research.DARKNESS.stage.1", "Saint Adelina described them as \"inhuman, repulsive affronts to nature\", stating that \"not to crusade against such heresy would be our gravest sin\".\\n" +
                "Her betrothed, Jerome, would be only mildly less vicious, deeming them \"misbegotten sons of earth and water\".\\n" +
                "Were they wrong? The people of the fishing village were certainly unwelcoming. Blatant in their scorn for me and for acts I did not commit.\\n" +
                "Yet, I could hear fear ailing their voice, see it diverting their eyes. \\n" +
                "In concealing — or eradicating — their existence, the church of my forefathers would have been rid of a people worshipping nothing less than the human mind's inability to grasp the truths of the world — ours and others. Heretical, by any means.\\n" +
                "But dreams can go through Him, he said.");

        add("research.EMPTY_DREAMS.title", "Empty Dreams");
        add("research.reminisced_void_metal.text", "Dream of Void, then Metal.");
        add("research.EMPTY_DREAMS.stage.0", "A memory of a dream I cannot remember, older yet than darkness, predating reality (or that which we call so). He would then live (exist) down there, deceitful (with or without intent), waiting (outside of time), dreaming.\\n" +
                "Where lies the lie? Were my eyelids sewn shut at birth, dogmas drawn in their insides to see nothing else? Or am I too quick to dismiss the old adages, their wisdom shown in their persistence, in their supremacy over the heretical folks whom they… they…\\n" +
                "No. I must be rigorous. An empty dream, one I cannot reminisce, at the tip of my mind but barely outside its grasp. Yet my next dream might, and learn that it must slow down its descent, stay still within the Void, the Veil, the Dreamer.");
        add("research.EMPTY_DREAMS.stage.1", "It worked.");

        add("research.WATER_DREAMS.title", "Dreams of Water");
        add("research.reminisced_void_water.text", "Dream of Void, then Water.");
        add("research.WATER_DREAMS.stage.0", "Our mind stands atop the small, sinking isle of what it sees and feels, besieged on all fronts by the endless waves of the unknown.\\n" +
                "Now the tide rises, the illusion shatters, and what we thought we knew is submerged once more.\\n" +
                "Lies. Some perpetrated by thousand year old institutions, busy polishing unearned offerings; others encouraged by our own — perhaps merciful — minds, and the wishful thinking clouding our thoughts.\\n" +
                "But we know dreams can show us more than meets the eye. Fix our lapses in judgement, grant us a new perspective. Breach our preconceived notions, this veil obscuring the unknown.\\n" +
                "The shoremen were right. I must dream of Water, a memory so intense I may forget to breathe.\\n" +
                "But first I must bolster it with Void, to still its descent, and give myself time to… listen.\\n" +
                "...\\n" +
                "Merciful Gods, please,\\1" +
                "don't let me drown.");
        add("research.WATER_DREAMS.stage.1", "Gracious Gods, do not let my mind crumble as I write this.\\n" +
                "My dream dived in, my body forgotten atop the shore. The mind knew no longer our material world. \\n" +
                "I sped down, relentless, unremitting, as I became engulfed in the rumble of water, sweetly scented in the color of nothing. It was the fusion of the senses, a synesthesia of the soul.\\n" +
                "Then, a voice. It came from the ocean, speaking not to me, but through me, as if it did not notice I was there, in my dream.\\n" +
                "The voice of the sea. Deafening currents running fast and mindless in the deepest trenches. Life is born this way, the turmoil of waters becoming our blood.\\n" +
                "I know now what I must do. The keeper was right.");

        add("research.BLACK_MIRROR.title", "Mirror of the Soul");
        add("research.must_weep.text", "Talk to the lighthouse keeper.");
        add("research.mirror_wept.text", "Gaze in the mirror.");
        add("research.BLACK_MIRROR.stage.0", "I must talk to him.");
        add("research.BLACK_MIRROR.stage.1", "Tears are born of fear, and thus I must ask myself, what is it I fear most?\\n" +
                "I must dig deep, and look in the uncomfortable places beneath that first veil that is myself.\\n" +
                "I will build a mirror, but it shall be a dark mirror, black as black can be, giving off no reflection. I will peer into nothing but my own soul.");
        add("research.BLACK_MIRROR.stage.2", "It is done.");

        add("research.COMMUNION.title", "Communion");
        add("research.must_commune.text", "Talk to the lighthouse keeper once more.");
        add("research.communed.text", "Commune with the Idol in the town square.");
        add("research.COMMUNION.stage.0", "The old falsehoods plague my mind, parasites blinding me inside out.\\nI know that I do not know. Is this what the shoremen profess?");
        add("research.COMMUNION.stage.1", "I humbly pledge my life to You, Great Dreamer.\\n" +
                "I disavow all my previous beliefs, any gods I may have previously had faith in.\\n" +
                "I am insignificant to you. The ocean, Your boundless presence on the Overworld, is symbol of that.\\n" +
                "You are unknowable to me. The Void, full of emptiness, is Your inscrutable substance.\\n" +
                "Beyond You lie all things. The Truth, hidden by the Veil.\\n" +
                "Grant us knowledge, so we may undo all lies.\\n" +
                "Grant us strength, so we may survive the Truth.\\n" +
                "Grant us courage... so we may hear Your Holy Voice.");

        add("research.CORRESPONDENCE.title", "Correspondence");
        add("research.CORRESPONDENCE.stage.0", "It would be useful to begin a letter exchange with the shoremen, in order to avoid having to travel to their hamlet for every need.\\n" +
                "I should create a letter box, so that I may send and receive mail to and from the wider world.");

        add("research.SURGERY.title", "Surgery");
        add("research.rationalized.text", "Rationalize your future deeds in the black mirror.");
        add("research.incised.text", "Perform an incision, either on the chest or back.");
        add("research.SURGERY.stage.0", "To commune is a revelation: we are not made in the face of God. Our bodies are deformed, misshapen by ephemerality.\\n" +
                "This epiphany tears my heart, and fills me with doubt. What divides us from the divine? We do not know and we cannot know, for our skin is the veil hiding all that lies within.\\n" +
                "But like the outer layer of a cocoon, what if it is preparing us for a final metamorphosis?\\n" +
                "This question deserves enquiry. I do not know how. One would have to examine human bodies in the depths and cavities of their flesh and guts, pierce through organ and bone and wash off bile and blood. I cannot forfeit so the lives of mortal men.  \\n" +
                "But no one else would do it. And the greatest of all questions would remain unanswered.\\n" +
                "Who and what are we? Where do we come from?\\n" +
                "And what will we become?");
        add("research.SURGERY.stage.1", "Then it is decided. I will begin a journal, to keep track of my experiments. There I shall record all my knowledge, and all the operations I have performed.\\n" +
                "It'll take time, but I must build the required infrastructure and craft the necessary tools for surgery. I will then perform a simple operation, for practice.");
        add("research.SURGERY.stage.2", "The incision was successful. The human body is now open to me.");

        add("research.HEARTS.title", "To gain life, I must grant death");
        add("research.extracted_heart.text", "Extract an intact heart.");
        add("research.HEARTS.epigraph", "§oDo not be deceived,\\0" +
                "All blood spill forges a chain,\\0" +
                "And what worth is a king in shackles?§r\\1");
        add("research.HEARTS.epigraph.source", "Saint Jerome's celebrated §oRebuke§r\\n");
        add("research.HEARTS.stage.0", "Connections are sewn in blood – that thread linking all beings, living and dead. It is the bond holding lives together, within and between, and its vessels run deep through bone and earth until they reach its ancient progenitor: water.\\n" +
                "Blood is both life and death, as well as the chain betwixt. To deprive a being of its heart is to kill it, and yet no life is lost.\\n" +
                "The extraction of a heart requires an incised chest on the patient, and is very painful. Injecting a sufficient amount of softener before the extraction is crucial, or the pain will be too intense for the organ to be successfully extracted. Either way, the patient will not survive.");
        add("research.HEARTS.stage.1", "The heart still beats. When placed on the ground, the sound will attract close by undead (is it some sort of nostalgia that drives them towards life?)\\n" +
                "The organ itself, however, may also be used for other purposes. The practice of blood rituals has been celebrated my many peoples throughout history, and has been extensively documented by academia. There is much more to be done here.");

        add("research.SPINES.title", "Willpower resides in the spine");
        add("research.extracted_spine.text", "Extract a spine, then release the patient.");
        add("research.SPINES.stage.0", "What makes a human stand tall and straight in the face of adversity, and to always rise from every fall?\\n" +
                "The parallels are clear, and the millennia-old syncretism of the studies of body and soul is precisely what I seek to delve into. To deprive a human of its spine – however sickening – may prove the link with its power of will. \\n" +
                        "A spine extraction is a painful operation that requires an incision to be present.");
        add("research.SPINES.stage.1", "The convalescent can no longer walk, though this does not prove much. I sadly do not have a proper way – especially with a patient so crippled – to verify their lack or presence of willpower. Not yet, at least. However, the knowledge of how to extract a spine may prove a valuable resource, along with the spines themselves.");

        add("research.CUSTOMS.title", "Customs of the Shoremen");
        add("research.ate_slug.text", "Eat a slug.");
        add("research.CUSTOMS.epigraph", "§o\\[...\\] and like two grains of sand, swept across dunes, never to meet again.\\0" +
                "Yet, for that one moment, they loved one another. \\0" +
                "Solidarity, compassion, these are the truest of human emotions.\\0" +
                "There is no veil here.§r\\1");
        add("research.CUSTOMS.epigraph.source", "Tales of the Desert Folk.\\n");
        add("research.CUSTOMS.stage.0", "Be it man's nature to loathe or cherish, the fear of the unknown \"Other\" can hide all sorts of wonders – and so it is that those renouncing such hate may experience the exquisite weirdness of a new, exotic culture. Starting from food, of course.\\n" +
                "I wish to learn of and embrace the many customs of the shoremen, and those white, slimy slugs, which seem to play such a major role in their way of life, would be a great starting point. I can talk or send a letter to the lighthouse keeper if I need a sample.");
        add("research.CUSTOMS.stage.1", "§o...bleah.§r\\n" +
                "Some veils are better left closed.");

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
        add("reminiscence.void", "My next dream will be empowered by void.");
    }

    private void addGuis() {
        add("gui.sleep_chamber", "Sleep Chamber");
        add("gui.sleep_chamber.wake", "Leave chamber");

        addJournal();
        addLetterBox();


        add("gui.dialogue.bartender.display_name", "Bartender");
        add("gui.dialogue.carpenter.display_name", "Carpenter");
        add("gui.dialogue.clerk.display_name", "Clerk");
        add("gui.dialogue.drunk.display_name", "Drunk");
        add("gui.dialogue.fisherman.display_name", "Fisherman");
        add("gui.dialogue.lighthouse_keeper.display_name", "Lighthouse Keeper");
        add("gui.dialogue.miner.display_name", "Miner");
        add("gui.dialogue.scholar.display_name", "Scholar");
        add("gui.dialogue.smith.display_name", "Smith");
    }

    private void addLetterBox() {
        add("gui.letter_box.received", "Received");
        add("gui.letter_box.sent", "Sent");
        add("gui.letter_box.new", "New");
        add("gui.letter_box.send", "Send");
        add("gui.letter_box.reply", "Reply");
        add("gui.letter_box.redeem", "Redeem");
    }

    private void addJournal() {
        add("gui.journal.bookmark.overview", "Overview");
        add("gui.journal.bookmark.tools", "Tools");
        add("gui.journal.bookmark.ingredients", "Ingredients");
        add("gui.journal.bookmark.journal", "Journal");
        add("gui.journal.bookmark.abominations", "Abominations");

        add("gui.journal.overview.fundamentals", "Fundamentals");
        add("gui.journal.overview.patients", "Obtaining patients");
        add("gui.journal.overview.starting", "Starting the operation");
        add("gui.journal.overview.managing", "Managing pain");
        add("gui.journal.overview.concluding", "Concluding the operation");
        add("gui.journal.overview.journal", "Journal");
        add("gui.journal.overview.ingredients", "Ingredients");
        add("gui.journal.overview.fluids", "Fluids & Serums");
        add("gui.journal.overview.solids", "Solids");
        add("gui.journal.overview.types", "Operation types");
        add("gui.journal.overview.extraction", "Extraction");
        add("gui.journal.overview.incision", "Incision");
        add("gui.journal.overview.injection", "Injection");
        add("gui.journal.overview.insertion", "Insertion");
        add("gui.journal.overview.stitching", "Stitching");
        add("gui.journal.overview.infrastructure", "Infrastructure");
        add("gui.journal.overview.distillation", "Distillation");
        add("gui.journal.overview.storage", "Storage");
        add("gui.journal.overview.holding", "Holding patients");
        add("gui.journal.overview.surgery", "Surgery");
        add("gui.journal.overview.patients.text", "The primary resource in a surgeon’s studies is none other than the patients themselves: humans, such as the villagers scattered across the world (though I shall abstain from using shoremen).\\n" +
                "Understandably, it should not be expected for the patients to join the experiments willingly. They must be coerced and, sadly, this implies the use of force. I can use a {§nblackjack§r}[caption:journal.blackjack] to temporarily knock out a villager. I can then pick up and carry any incapacitated villagers lying on the ground. If necessary, I can set down any villager I’m carrying and it will hopefully get back up soon.");
        add("gui.journal.overview.starting.text", "Any patient I’m currently carrying may be {§nplaced§r}[caption:right_click] atop a surgery bed.\\n" +
                "This allows the surgeon to perform operations on either the §lChest§r or the §lBack§r. One may {§nswitch§r}[caption:shift_right_click] the patient’s position to allow for surgeries on either location, though only when there is no currently open incision. \\n" +
                "All operation types can be performed on the surgery bed.");
        add("gui.journal.overview.starting_cradle.text", "Any patient I’m currently carrying may be {§nplaced§r}[caption:right_click] atop a surgery bed or within a watery cradle.\\n" +
                "Operations performed in the watery cradle target the §lSkull§r, and only injections may be executed.\\n" +
                "The surgery bed, on the other hand, allows the surgeon to perform operations on either the §lChest§r or the §lBack§r. One may {§nswitch§r}[caption:shift_right_click] the patient’s position to allow for surgeries on either location, though only when there is no currently open incision. \\n" +
                "All operation types can be performed on the surgery bed.");
        add("gui.journal.overview.managing.text", "Sedation is paramount in any successful surgery. Aside from the ethical concerns, patients in severe pain will indubitably thrash and flail about, virtually ensuring grave injuries and death when using surgery tools upon them.\\n" +
                "It is thus imperative to inject sufficient quantities of sedative during an operation. Doing so while the patient is already calm will help prevent subsequent pain. For example, injecting sufficient sedative before starting an incision will prevent patients from acting out and injuring themselves with the scalpel.\\n" +
                "If the patient is in pain, injecting sedative will calm them down. It is wise to stop an operation such as an incision at the first sign of pain to prevent it going awry, and only continuing it after the patient has calmed down and has been sedated enough to prevent further pain.\\n" +
                "Finally, it should be noted that excessive quantities of sedative pose a high risk, and may result in the patient’s death.");
        add("gui.journal.overview.concluding.text", "The success of an operation depends on the desired outcome. Although a premature death will most likely make any further operations impossible, it may be tolerated as long as the necessary results (such as an organ’s extraction) have already been obtained. The death of a patient is certainly a loss, but not a tragedy.\\n" +
                "Of course, there are times when we seek the patient’s survival. In general, most patients will exit the surgery either (i) alive and well, (ii) crippled (potentially for life), or (iii) deceased.");
        //TODO add("gui.journal.overview.journal.text", ...
        add("gui.journal.overview.fluids.text", "Liquid ingredients, such as {§nsedative§r}[link:journal.sedative], {§ncoagulant§r}[link:journal.coagulant], and several others, can be injected into patients to achieve various results. This requires a {§nsyringe§r}[link:journal.syringe], and does not require an incision on the patient.\\n" +
                "Such fluids may be stored in liquid flasks ({§nsmall§r}[link:journal.small_flask], {§nmedium§r}[link:journal.medium_flask] and {§nlarge§r}[link:journal.large_flask]), and are often produced with the help of {§nalembics§r}[link:journal.alembics].");
        add("gui.journal.overview.solids.text", "Solid ingredients may be inserted in the patient. This requires {§nforceps§r}[link:journal.forceps], and can only be done when the incision is open.\\n" +
                "Such ingredients are generally stored in appropriate containers, such as {§nitem jars§r}[link:journal.item_jar].");
        add("gui.journal.overview.extraction.text", "Extractions can be performed either on the back or the chest, and are used to obtain respectively the patient’s spine or heart. They always require an incision to be already present, and will likely cause severe bleeding after the operation. They are generally very painful.\\n" +
                "Extractions are performed with {§ntongs§r}[link:journal.tongs]. Interrupting one resets progress.");
        add("gui.journal.overview.incision.text", "Incisions can be performed either on the back or the chest, and are used to open up a wound that may then allow insertions or extractions. A patient cannot be moved (neither removed from the surgery bed nor flipped) while incised. They are generally very painful.\\n" +
                "Incisions are performed with the {§nscalpel§r}[link:journal.scalpel]. Interrupting one resets progress.");
        add("gui.journal.overview.injection.text", "Injections can be performed on any surgical location: back, chest and skull. They are carried out with a {§nsyringe§r}[link:journal.syringe] after it has been filled with fluids. Interrupting an injection does not reset progress, as the fluid already resides within the patient’s body.\\n" +
                "Injections may be painful depending on the fluid type and quantity.");
        add("gui.journal.overview.insertion.text", "Insertions can be performed either on the back or the chest, and always require an incision to be present. They are performed with {§nforceps§r}[link:journal.forceps], after they have been used to grab a solid ingredient. Interrupting an insertion resets progress.");
        add("gui.journal.overview.stitching.text", "Stitching is used to sew the incision made through a scalpel. It is performed with {§nthread and needle§r}[link:journal.sewing_needle]. Stitching is painless and instantaneous.");

        add("gui.journal.tools.forceps.title", "Forceps");
        add("gui.journal.tools.forceps.text", "A tool necessary for insertions. Can pick up a (single) item when used on a flask containing a solid ingredient.\\nForceps with an item inside may be applied on an incised patient on a surgery bed.");

        add("gui.journal.tools.scalpel.title", "Scalpel");
        add("gui.journal.tools.scalpel.text", "A tool necessary for incisions. Can be used on the surgery bed.\\nSharp and painful, yet very precise. Killing enemies with such an instrument may allow for some body parts to be salvaged.");

        add("gui.journal.tools.sewing_needle.title", "Sewing Needle");
        add("gui.journal.tools.sewing_needle.text", "A tool necessary for sewing. Quick and easy to use.");

        add("gui.journal.tools.syringe.title", "Syringe");
        add("gui.journal.tools.syringe.text", "Syringes can {§ndrain§r}[caption:shift_right_click] and {§nfill§r}[caption:right_click] liquid flasks at the milliBucket granularity. They are necessary for {§ninjections§r}[caption:right_click] on patients.");

        add("gui.journal.tools.tongs.title", "Tongs");
        add("gui.journal.tools.tongs.text", "A tool necessary for extractions. Needs an open incision first.");

        add("gui.journal.tools.flasks.title", "Fluid flasks");
        add("gui.journal.tools.flasks.text", "Flasks come in various shapes and sizes. They are used to store fluids of all sorts, and are necessary for syringes to be filled.\\nFlasks may be placed on the ground or, more compactly, on a flask shelf.");

        add("gui.journal.tools.alembics.title", "Alembics");
        add("gui.journal.tools.alembics.text", "A device used to concoct various types of fluids. The first and leftmost alembic as well as the third one can store input fluids used in crafting. The second, small one can accept input items.\\nA fluid flask must be placed to accept the output. If the input fluid, item and second fluid make for a valid recipe, the flask will slowly start getting filled.\\nA given input item may be sufficient for a certain amount of output fluid.");

        add("gui.journal.tools.flask_shelf.title", "Flask Shelf");
        add("gui.journal.tools.flask_shelf.text", "A set of shelves where fluid and item flasks may be placed in a more compact manner. Any such flasks may then be interacted with normally, as if they were on the ground.");

        add("gui.journal.tools.surgery_bed.title", "Surgery Bed");
        add("gui.journal.tools.surgery_bed.text", "This is where surgical operations on the chest and back are performed.");

        add("gui.journal.tools.watery_cradle.title", "Watery Cradle");
        add("gui.journal.tools.watery_cradle.text", "A masterwork of engineering, capable of setting the enclosed patient in a trance and allowing for operations on the brain.\\nOnly injections may be performed in the watery cradle.");


        add("gui.journal.ingredients.plucked_eye.title", "Plucked Eye");
        add("gui.journal.ingredients.plucked_eye.text", "To look at yourself from the inside, escaping all veils of skin and bone – it drives one to madness.\\n§oAdds:§r §lfolly§r.\\n§oRequires:§r 1 capacity.");

        add("gui.journal.ingredients.shell.title", "Shell");
        add("gui.journal.ingredients.shell.text", "A shell of some crustacean or mollusk. It is a lethal reminder of our lack of any such protection, of our defenselessness.\\n§oAdds:§r §lvulnerability§r.\\n§oRequires:§r 1 capacity.");

        add("gui.journal.ingredients.tiny_skull.title", "Tiny Skull");
        add("gui.journal.ingredients.tiny_skull.text", "No fight. No flight. Such creatures know only fright.\\n§oAdds:§r §lterror§r.\\n§oRequires:§r 1 capacity.");

        add("gui.journal.ingredients.acid_gland.title", "Acid Gland");
        add("gui.journal.ingredients.acid_gland.text", "Produces a highly corrosive substance, melting through armor.\\n§oAdds:§r §larmor damage§r.\\n§oRequires:§r 1 capacity.");

        add("gui.journal.ingredients.fertilizer_gland.title", "Fertilizer Gland");
        add("gui.journal.ingredients.fertilizer_gland.text", "Convalescents possessing this gland will fertilize the soil where they tread.");

        add("gui.journal.ingredients.marrow_gland.title", "Marrow Gland");
        add("gui.journal.ingredients.marrow_gland.text", "This gland continuously produces marrow, resulting in bone meal being regularly dropped by the convalescent.");

        add("gui.journal.ingredients.silk_gland.title", "Silk Gland");
        add("gui.journal.ingredients.silk_gland.text", "Spiders and similar creatures use these to build their homes – and snare their targets.\\n§oAdds:§r §lenweb§r.\\n§oRequires:§r 1 capacity.");

        add("gui.journal.ingredients.gunpowder_bladder.title", "Gunpowder Bladder");
        add("gui.journal.ingredients.gunpowder_bladder.text", "Dropped by creepers and similarly explosive creatures when slain in a precise, surgical manner. Entities possessing this bladder may turn explosive when set on fire.");

        add("gui.journal.ingredients.living_iron.title", "Living Iron");
        add("gui.journal.ingredients.living_iron.text", "Living Iron");

        add("gui.journal.ingredients.empty_bladder.title", "Empty Bladder");
        add("gui.journal.ingredients.empty_bladder.text", "Empty Bladder");


        add("gui.journal.journal.type.none", "Select Type");
        add("gui.journal.journal.type.position", "Position");
        add("gui.journal.journal.type.extraction", "Extraction");
        add("gui.journal.journal.type.incision", "Incision");
        add("gui.journal.journal.type.injection", "Injection");
        add("gui.journal.journal.type.insertion", "Insertion");
        add("gui.journal.journal.type.stitching", "Stitching");
        add("gui.journal.journal.type.pain", "Pain");
        add("gui.journal.journal.type.death", "Death");
        add("gui.journal.journal.completeness.complete", "Completed");
        add("gui.journal.journal.completeness.incomplete", "Not completed");
        add("gui.journal.journal.ingredient.none", "Select Ingredient");
        add("gui.journal.journal.fluid.none", "Select Fluid");
        add("gui.journal.journal.new", "New Report");
        add("gui.journal.journal.edit", "Edit");
        add("gui.journal.journal.save", "Save");
        add("gui.journal.journal.delete", "Delete");
        add("gui.journal.journal.cancel", "Cancel");
    }

    private void addSurgery() {
        add("surgery.status.status", "Status: ");
        add("surgery.status.arsenal", "Burst Effect: ");
        add("surgery.status.arsenal_amplifier", "Effect Amplifier: ");
        add("surgery.status.arsenal_duration", "Effect Extender: ");
        add("surgery.status.burst_extension", "Burst Extender: ");
        add("surgery.status.mutex", "Mutex: ");
        add("surgery.status.trigger_type", "Trigger: ");
        add("surgery.status.target_type", "Target: ");
    }

}
