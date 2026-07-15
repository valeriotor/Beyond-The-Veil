package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.GearBenchBlock;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
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
        add(Registration.CANOE.get(), "Canoe");
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
        add(BLOOD_GEM.get(), "Blood Gem");
        add(BLOOD_THESIS.get(), "Sanguis: inter vitam et mortem");
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

        add("tooltip.sample_tube.stored", "Contains 100mB of %1$s.");
        add("tooltip.memory_phial.stored", "This phial stores %1$s.");
        add("tooltip.memory_phial.empty", "This phial is empty.");
        add("tooltip.pillar.bound", "Bound entity: ");
        add("tooltip.pillar.connected", "Connected");
        add("tooltip.sigil.player", "Bound to %1$s.");
        add("tooltip.sigil.area", "Bound to x: %1$s, y: %2$s, z: %3$s.");
        add("tooltip.repair_hammer.1", "§eUsed to repair Nautilus (left-click) or pick it up (right-click).");
        add("tooltip.repair_hammer.2", "§e§lOnly works from outside.§r");

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
        addMobInteracts();
        addExchanges();
        addResearch();
        addCraftingRegistry();
        addReminiscence();
        addGuis();
        addSurgery();
        addCommonCaptions();
        addContact();
        addItems();
        addArsenalAndEffects();
        addMemories();
        addEntities();
    }

    private void addEntities() {
        add(BTVEntities.SHOREMAN.get(), "Shoreman");
        add(BTVEntities.BLOOD_CULTIST.get(), "Blood Cultist");
        add(BTVEntities.ABOMINATION_0.get(), "Abomination (Tier 1)");
        add(BTVEntities.ABOMINATION_1.get(), "Abomination (Tier 2)");
        add(BTVEntities.ABOMINATION_2.get(), "Abomination (Tier 3)");
        add(BTVEntities.FLETUM.get(), "Fletum");
        add(BTVEntities.WEEPER.get(), "Weeper");
        add(BTVEntities.CRAWLER.get(), "Villager");
        add(BTVEntities.NAUTILUS.get(), "Nautilus");
        //add(BTVEntities.BLOOD_WRAITH.get(), "Blood_wraith");
        add(BTVEntities.BLOOD_ZOMBIE.get(), "Blood Zombie");
        add(BTVEntities.BLOOD_SKELETON.get(), "Blood Skeleton");
        add(BTVEntities.SURGEON.get(), "Surgeon");
        add(BTVEntities.SURGEON_LARVA.get(), "Surgeon Larva");
        add(BTVEntities.DEEP_ONE.get(), "Deep One");
        add(BTVEntities.CEPHALOPODIAN.get(), "Cephalopodian");
        add(BTVEntities.ANGLER.get(), "Angler");
        add(BTVEntities.SEA_SNAKE.get(), "Sea Snake");
        add(BTVEntities.SEPIID.get(), "Sepiid");
        add(BTVEntities.ADELINE.get(), "Adeline");
        add(BTVEntities.BONECAGE.get(), "Bonecage");
        add(BTVEntities.MAN_O_WAR.get(), "Man o' war");
        add(BTVEntities.OCTID.get(), "Octid");
        add(BTVEntities.UMANCALA.get(), "Umancala");
        add(BTVEntities.SANDFLATTER.get(), "Sandflatter");
        add(BTVEntities.JELLY.get(), "Jelly");
        add(BTVEntities.LIVING_PORTAL.get(), "Living Portal");
        add(BTVEntities.CANOE.get(), "Canoe");
        add(BTVEntities.UMANCALA_FIREBALL.get(), "Umancala Fireball");
        add(BTVEntities.DREAM_FOCUS_ITEM.get(), "Dream Focus Item");
        add(BTVEntities.DREAM_FOCUS_FLUID.get(), "Dream Focus Fluid");
    }

    private void addMobInteracts() {
        add("interact.surgeon.bad_report_location_cradle", "Note: this report uses positions that are not allowed on the currently bound watery cradle");
        add("interact.surgeon.bad_report_location_bed", "Note: this report uses positions that are not allowed on the currently bound surgical bed");
        add("interact.surgeon.bad_be_location_cradle", "Note: this cradle does not allow the positions assigned to the surgeon in the report.");
        add("interact.surgeon.bad_be_location_bed", "Note: this bed does not allow the positions assigned to the surgeon in the report.");
    }

    private void addMemories() {
        add("memory.toast.title", "New Memory Gained.");
        add("memory.prefix", "Memory of %1$s");

        add("memory.animal.name", "Animal");
        add("memory.animal.brief", "Obtained after witnessing the miracle of birth. Used to call forth beasts and critters.");
        add("memory.animal.0.1", "An animal will find its way right next to me. What sort? That depends on what I am holding in my hand: any item associated with a given animal is likely to summon that kind of creature. For example, eggs are going to call forth chickens; leather, on the other hand, may be more ambiguous.");
        add("memory.animal.2.1", "§oPath Sigil§r: The animal is called forth at the target area instead.");
        add("memory.animal.4.1", "§oImmortal Sigil§r: The animal is called forth next to the target human instead.");

        add("memory.beheading.name", "Decapitation");
        add("memory.beheading.brief", "A gruesome memory – how could one forget such a thing?");
        add("memory.beheading.0.1", "The dream... I think it fashions a copy of §omy own head§r. Or perhaps, it just retrieves one from a previous death of mine. Or could it be a future death? Whatever the method, I am frightened at the implications – should I teach my dreams to do such things?");
        add("memory.beheading.4.1", "§oImmortal Sigil§r: Gives me a head of the target human instead. The target will not know – I hope.");

        add("memory.change.name", "Change");
        add("memory.change.brief", "Relentless, unremitting, the churning currents of Arche are the chaos that spawned all beings. Change brings life – and death, as I now know.");
        add("memory.change.0.1", "All the negative status effects ailing me are either converted to their beneficial counterpart (e.g. poison becoming regeneration), or removed.");
        add("memory.change.1.1", "↳ §lVoid§r: It is positive effects being turned into negative ones instead.");
        add("memory.change.2.1", "§oPath Sigil§r: Affects all non-human creatures within a certain range of the target area instead.");
        add("memory.change.4.1", "§oImmortal Sigil§r: Affects the target human instead.");

        add("memory.crystal.name", "Crystal");
        add("memory.crystal.brief", "The shining gleam of precious gems. Uses to probe the ground below for similar treasures.");
        add("memory.crystal.0.1", "Like Metal, this dream reveals to me the presence of ores in the ground below me: this time, it is gems such as diamonds or emeralds.");
        add("memory.crystal.1.1", "↳ §lVoid§r: Increases the search radius.");
        add("memory.crystal.2.1", "§oPath Sigil§r: Starts the search at the target area.");
        add("memory.crystal.4.1", "§oImmortal Sigil§r: Starts the search below the target human.");

        add("memory.darkness.name", "Darkness");
        add("memory.darkness.brief", "An ancient memory.");
        add("memory.darkness.0.1", "Reminiscing points me to a direction – similar to dreams of sentience, but perceived in a different shade.");
        add("memory.darkness.0.2", "Leads me to the Black Shores.");
        add("memory.darkness.4.1", "§oImmortal Sigil§r: Blinds the target human.");
        add("memory.darkness.5.1", "↳ §lVoid§r: Makes the blindness even more severe.");

        add("memory.death.name", "Death");
        add("memory.death.brief", "The act of passing on, and, in my case, of coming back to life. If only I could remember more!");
        add("memory.death.0.1", "By reminiscing, I am shown the point where I last died.");
        add("memory.death.1.1", "↳ §lVoid§r: Instead, I am shown the point where I last respawned. Life and death, intrinsically tied.");
        add("memory.death.2.1", "§oPath Sigil§r: No effect, as far as I can tell.");
        add("memory.death.4.1", "§oImmortal Sigil§r: I am shown the target's last death instead of my own.");
        add("memory.death.5.1", "↳ §lVoid§r: Instead, I am shown the point where the target last respawned.");

        add("memory.heartbreak.name", "Heartbreak");
        add("memory.heartbreak.brief", "An irrepressible memory.");

        add("memory.introspection.name", "Introspection");
        add("memory.introspection.brief", "Introspection");

        add("memory.metal.name", "Metal");
        add("memory.metal.brief", "A common memory among those experienced in mining – that of striking ore – yet this is of a different sort, as if in it the iron was felt by thought, rather than touch.");
        add("memory.metal.0.1", "This dream explores the ground below me, revealing to me the presence of metal ores such as iron or gold. I can scroll through the various layers that have been probed.");
        add("memory.metal.1.1", "↳ §lVoid§r: Increases the search radius.");
        add("memory.metal.2.1", "§oPath Sigil§r: Starts the search at the target area.");
        add("memory.metal.4.1", "§oImmortal Sigil§r: Starts the search below the target human.");

        add("memory.plant.name", "Flora");
        add("memory.plant.brief", "Flora");

        add("memory.power.name", "Power");
        add("memory.power.brief", "Felling a fearsome foe is a cherished memory – though I should not let it go to my head.");
        add("memory.power.0.1", "Increases the power of my status effects.");
        add("memory.power.1.1", "↳ §lVoid§r: Further increases their power.");
        add("memory.power.2.1", "§oPath Sigil§r: Affects all non-human creatures within a certain range of the target area instead.");
        add("memory.power.4.1", "§oImmortal Sigil§r: Affects the target human instead.");

        add("memory.repair.name", "Mending");
        add("memory.repair.brief", "The satisfaction of a job well done. Even inanimate things deserve some love.");
        add("memory.repair.0.1", "Repairs the armor I'm wearing and the items I'm wielding in my hands.");
        add("memory.repair.1.1", "↳ §lVoid§r: Further repairs them.");
        add("memory.repair.4.1", "§oImmortal Sigil§r: Affects the target human instead.");
        add("memory.repair.5.1", "↳ §lVoid§r: The target's worn and wielded items are §ldamaged§o instead.");

        add("memory.sentience.name", "Sentience");
        add("memory.sentience.brief", "With action comes reaction, but of a thoughtful sort – non-deterministic. Is it a worthy definition, or just wishful thinking?");
        add("memory.sentience.0.1", "When I reminisce, I should look around. Eventually, I should see it point me towards the place visited by the dream.");
        add("memory.sentience.0.2", "When I reminisce, I should look around. Eventually, I should see it point me towards the closest village.");
        add("memory.sentience.1.1", "↳ §lVoid§r: Points me to a place where sentience once lay, yet no longer. Is time just a fourth axis to a dream, no different from the other three?");
        add("memory.sentience.2.1", "§oPath Sigil§r: Starts the search from the target area instead.");
        add("memory.sentience.4.1", "§oImmortal Sigil§r: Starts the search from the target human instead.");

        add("memory.stillness.name", "Stillness");
        add("memory.stillness.brief", "The body lies motionless, but the mind swirls and slews. In sleep, daytime roles reverse: I am still, and my dreams roam free.");
        add("memory.stillness.0.1", "Increases the duration of my status effects.");
        add("memory.stillness.1.1", "↳ §lVoid§r: Further increases their duration.");
        add("memory.stillness.2.1", "§oPath Sigil§r: Affects all non-human creatures within a certain range of the target area instead.");
        add("memory.stillness.4.1", "§oImmortal Sigil§r: Affects the target human instead.");

        add("memory.tool.name", "Tools");
        add("memory.tool.brief", "Tools");

        add("memory.void.name", "Void");
        add("memory.void.brief", "A memory of a memory. All I remember is... forgetting.");
        add("memory.void.0.1", "The next time I dream, the new memory's effects may be changed – often strengthened. This may not work on all dreams.");
        add("memory.void.4.1", "§oImmortal Sigil§r: Affects the target human instead.");

        add("memory.water.name", "Water");
        add("memory.water.brief", "We are all born from water – so why, all of a sudden, does it feel so alien?");
        add("memory.water.0.1", "Locates an ocean monument.");
        add("memory.water.1.1", "↳ §lVoid§r: Hear the Ocean's voice.");
        add("memory.water.2.1", "§oPath Sigil§r: Starts the search from the target area instead.");
        add("memory.water.4.1", "§oImmortal Sigil§r: Encases the target in ice.");

    }

    private void addItems() {
        add("message.vessel_stone.not_in_arche", "The vessel stone must be used while in Arche.");
        add("message.vessel_stone.in_water", "The vessel stone may not be used while in water.");
        add("message.dream_bottle.player_not_found", "Target not found.");
        add("message.dream_bottle.other_dimension", "The target area is in another dimension.");
    }

    private void addContact() {
        add("contact.error.night", "I must wait for the darkest hours of night to make contact.");
        add("contact.error.players", "I must be alone to make contact.");

    }

    private void addExchanges() {
        add("correspondence.to", "To: ");
        add("correspondence.from", "From: ");
        add("correspondence.awaits", "§4Awaits reply");
        add("correspondence.redeem", "§4Redeem items");
        add("correspondence.no_items", "Items required");
        add("correspondence.shoreman_lighthouse_keeper", "Lighthouse Keeper");
        add("correspondence.shoreman_scholar", "The Scholar");
        add("correspondence.blood_cult", "???");
        add("correspondence.blood_cult_2", "???");
        add("correspondence.miskatonic_researchers_blood", "Miskatonic University – Anthropological Studies");
        add("correspondence.arkham_sanitarium", "Arkham Sanitarium");

        add("exchange.scholar_offer_help.object", "My knowledge is at your disposal");
        add("exchange.scholar_offer_help.0.0.hello", "Dear Seeker,\\n");
        add("exchange.scholar_offer_help.0.1.this_is", "I am the Scholar of the shoremen, reaching you to express not only gratitude, but also great admiration: for your willingness to overcome those inner biases that gnaw at the mind of every human, for your desire to challenge your own beliefs in pursuit of what is true, and for your indomitable tenacity, unperturbed by the metanoia brought forth by communion.\\n");
        add("exchange.scholar_offer_help.0.2.feel_free", "I thus pledge myself to help in any way I can. I am knowledgeable about several topics, related both to our traditions here on the shore, and the wider academic circles I once belonged to. Do not hesitate to write or visit.\\n");
        add("exchange.scholar_offer_help.0.3.best", "Best,\\1The Scholar.");
        add("exchange.scholar_offer_help.1.0.hello", "Hello,\\n");
        add("exchange.scholar_offer_help.1.0.greetings", "Greetings,\\n");
        add("exchange.scholar_offer_help.1.0.dear", "Dear Scholar,\\n");
        add("exchange.scholar_offer_help.1.1.dreamer", "Knowing of the Great Dreamer has upended my perception of the world, and yet there is so much more I wish to learn of Him. What is your relationship with your deity, and how do you display your faith?\\n");
        add("exchange.scholar_offer_help.1.1.ocean", "I am intrigued by the fascination your kin holds towards the Ocean, and thus I wonder: what does it represent to you? Is Father Ocean a living creature, or simply a medium for dreams?\\n");
        add("exchange.scholar_offer_help.1.2.thanks", "I thank you for the opportunity.\\n");
        add("exchange.scholar_offer_help.1.2.see_you", "I hope to see you soon.\\n");
        add("exchange.scholar_offer_help.1.3.name", "%s");
        add("exchange.scholar_offer_help.1.3.friend", "Your friend");
        add("exchange.scholar_offer_help.1.3.seeker", "The Seeker");
        add("exchange.scholar_offer_help.1.3.traveller", "The Traveller");
        add("exchange.scholar_offer_help.2.0.hello", "Dear Seeker,\\n");
        add("exchange.scholar_offer_help.2.1.both", "Oh my! Such a compelling question, and with such complex answer(s). In short, understand that our belief is two-fold: the Great Dreamer is the central figure in our religion, whom we view as Creator and to whom we dedicate our prayers; the Ocean is closer to us, as He is our medium to the Dreamer – an Archpriest of sorts, whom we also call Arche, or Dagon.\\n");
        add("exchange.scholar_offer_help.2.2.in_person", "I am afraid, however, that there is simply too much to say on the subject. I propose that we meet to discuss in person, whenever you have time. I am available on most days (ah, the joys of retirement!)\\n");
        add("exchange.scholar_offer_help.2.3.best", "Best,\\1The Scholar.");

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
        add("exchange.keeper_ask_slugs.1.0.hello", "Dearest friend,\\n");
        add("exchange.keeper_ask_slugs.1.1.slugs", "It is my pleasure to satisfy your request. Indeed, it is touching to see someone so willing to embrace our culture.\\n");
        add("exchange.keeper_ask_slugs.1.2.warning", "Only, a small warning: they are an acquired taste.\\n");
        add("exchange.keeper_ask_slugs.1.3.best", "Warmly,\\1The Keeper.");

        add("exchange.keeper_baptism.object", "Baptism");
        add("exchange.keeper_baptism.0.0.friend", "Dear Friend,\\n");
        add("exchange.keeper_baptism.0.0.keeper", "Dear Keeper,\\n");
        add("exchange.keeper_baptism.0.1.baptism", "I wish to undergo the ritual you call \"Baptism\".\\n");
        add("exchange.keeper_baptism.0.1.ocean", "I wish to speak to the Ocean.\\n");
        add("exchange.keeper_baptism.0.2.common", "Knowing this to be a common practice among your kin, I would be glad if you could describe the steps needed to accomplish it.\\n");
        add("exchange.keeper_baptism.0.3.dreamer", "Just as I have communed with The Great Dreamer before, so too am I now not afraid to experience this new rite.\\n");
        add("exchange.keeper_baptism.0.3.embrace", "I wish to embrace your culture to the fullest, such is the fascination that I hold towards it.\\n");
        add("exchange.keeper_baptism.0.4.best", "Best,\\1");
        add("exchange.keeper_baptism.0.4.regards", "Kind Regards,\\1");
        add("exchange.keeper_baptism.0.5.name", "%s");
        add("exchange.keeper_baptism.0.5.friend", "Your friend");
        add("exchange.keeper_baptism.0.5.seeker", "The Seeker");
        add("exchange.keeper_baptism.0.5.traveller", "The Traveller");
        add("exchange.keeper_baptism.1.0.hello", "Dearest friend,\\n");
        add("exchange.keeper_baptism.1.1.ocean", "To speak with Father Ocean during baptism is a sacred act we perform when young. No outsider has ever attempted it before, because no outsider would survive it.\\n");
        add("exchange.keeper_baptism.1.2.rite", "The rite consists of consuming a white slug while submerged in water in a small hole surrounded by our dark sand.\\n");
        add("exchange.keeper_baptism.1.3.drown", "And, having done so, §oyou drown§r.\\n");
        add("exchange.keeper_baptism.1.4.death", "Though I know you are not afraid of death, perhaps, for once, you should be.\\n");
        add("exchange.keeper_baptism.1.5.luck", "I wish you good luck, friend.\\n");
        add("exchange.keeper_baptism.1.6.bye", "Faithfully,\\1The Keeper.");

        add("exchange.mauer_ask_thesis.object",             "Interest in Sanguis: Inter Vitam et Mortem");
        add("exchange.mauer_ask_thesis.0.0.all",            "Dear all,\\n");
        add("exchange.mauer_ask_thesis.0.0.colleagues",     "Dear colleagues,\\n");
        add("exchange.mauer_ask_thesis.0.1.independent",    "I am an independent researcher, striving to understand the bonds that tie all life.\\n");
        add("exchange.mauer_ask_thesis.0.1.surgeon",        "I am a successful surgeon, interested in the anatomy of the human body and its cultural significance across the world.\\n");
        add("exchange.mauer_ask_thesis.0.2.hopes",          "I write in hopes that you may send me a copy of your famous treatise: §oSanguis: Inter Vitam et Mortem§r.\\n");
        add("exchange.mauer_ask_thesis.0.3.regrettably",    "I am aware that it is, regrettably, no longer in print, but I hope you may help me nonetheless.\\n");
        add("exchange.mauer_ask_thesis.0.3.saddened",       "I was saddened to learn that it has been taken out of circulation, and strongly object to such choice: such a celebrated work of research should not undergo censorship!\\n");
        add("exchange.mauer_ask_thesis.0.4.best",           "Best Regards,\\1");
        add("exchange.mauer_ask_thesis.0.4.faithfully",     "Faithfully,\\1");
        add("exchange.mauer_ask_thesis.0.5.name",           "%s");
        add("exchange.mauer_ask_thesis.0.5.dr_name",        "Dr. %s");
        add("exchange.mauer_ask_thesis.0.5.seeker",         "The Seeker of Truth");
        add("exchange.mauer_ask_thesis.1.0.dear",           "Dear %s\\n");
        add("exchange.mauer_ask_thesis.1.1.thank",          "Thank you for your letter. Henry and I are glad to see such continued interest in our work. It is especially gratifying to see it coming from you—word has already travelled of your immortality (if these are only unfounded rumors, we sincerely apologize), and the topics discussed therein may be of particular consequence to you, just as they were to Adelina (we hope that you may one day meet her, if she returns from wherever she's gone).\\n");
        add("exchange.mauer_ask_thesis.1.2.most",           "Most unfortunately, however, we are unable to send you the treatise. Even the famously lax ethics department of the Miskatonic was forced to cede to public opinion, riled up by Jerome's §oRebuke§r five years ago, and no version of the treatise is available for external researchers.\\n");
        add("exchange.mauer_ask_thesis.1.3.what",           "What we §oare§r allowed to do, however, is grant you a copy of my Master's Thesis from twelve years ago (my, how time flies!), that served as the foundation for the subsequent treatise of the same name. It is a much abridged version of the full thing, yet still serves as a catalogue of several known rituals. You will find it attached to this letter.\\n");
        add("exchange.mauer_ask_thesis.1.4.if",             "If you ever become associated with our institution, you may be able to access the full treatise, which may contain several more notions of particular relevance to immortals, such as blood covenants.\\n");
        add("exchange.mauer_ask_thesis.1.5.all",            "All the best in your research.\\n");
        add("exchange.mauer_ask_thesis.1.6.dr",             "Warmly,\\1Dr. Georg Ferdinand Mauer");

        add("exchange.west_offer_surgeon.object",           "Surgeons for hire");
        add("exchange.west_offer_surgeon.0.0.dear",         "Dear Dr. %s\\n");
        add("exchange.west_offer_surgeon.0.1.word",         "Word has travelled of your interest in surgical practices. I hope your experiments are proceeding smoothly.\\n");
        add("exchange.west_offer_surgeon.0.2.as",           "As a word of advice—coming from someone with abundant experience—I would encourage you to focus on the intellectual tasks involved in experimentation, while delegating the menial repetition of such experiments to others.\\n");
        add("exchange.west_offer_surgeon.0.3.if",           "If this interests you, we would be glad to provide capable and precise surgeons for hire; all you would have to do is to provide them with clear instructions (I assume you are taking notes of your experiments?) and the necessary patients, ingredients, and indications on where to deposit the outcomes.\\n");
        add("exchange.west_offer_surgeon.0.4.the",          "The hiring cost is a small (though admittedly non-negligible) one-time payment of five diamonds.\\n");
        add("exchange.west_offer_surgeon.0.5.dr",           "Dr. H. West\\1");
        add("exchange.west_offer_surgeon.0.6.arkham",       "Arkham Sanitarium");
        add("exchange.west_offer_surgeon.1.0.all",          "Dear all,\\n");
        add("exchange.west_offer_surgeon.1.0.dr",           "Dear Dr. West,\\n");
        add("exchange.west_offer_surgeon.1.1.grateful",     "I am grateful for your advice. Indeed, I would be glad to keep close contact with your institution, in hopes of potential future collaborations.\\n");
        add("exchange.west_offer_surgeon.1.1.tempting",     "Your offer is tempting, although the price is indeed non-negligible. Nonetheless, I accept.\\n");
        add("exchange.west_offer_surgeon.1.2.enclose",      "I hereby enclose the five diamonds. Please inform me of any additional procedures involved in relocation, setting up the contract, and so forth.\\n");
        add("exchange.west_offer_surgeon.1.3.best",         "Best Regards,\\1");
        add("exchange.west_offer_surgeon.1.3.faithfully",   "Faithfully\\1");
        add("exchange.west_offer_surgeon.1.4.name",         "%s");
        add("exchange.west_offer_surgeon.1.4.dr_name",      "Dr. %s");
        add("exchange.west_offer_surgeon.1.4.seeker",       "The Seeker of Truth");
        add("exchange.west_offer_surgeon.2.0.dear",         "Dear Dr. %s\\n");
        add("exchange.west_offer_surgeon.2.1.thank",        "Thank you for your letter. Dr. West is not currently available, but has already informed me of your exchange.\\n");
        add("exchange.west_offer_surgeon.2.2.contract",     "Contract and relocation will not be necessary. I am enclosing the surgeon within this letter. Place it where you wish it to work.\\n");
        add("exchange.west_offer_surgeon.2.3.please",       "Please feed it raw meat regularly. Once it is fully grown, it will leave its cocoon behind and be ready to perform operations at your behest. \\n");
        add("exchange.west_offer_surgeon.2.4.we",           "We dearly hope the surgeon to meet your expectations, and would be glad to keep in touch. Please feel free to contact us if you need further help, or if you wish to hire additional surgeons.\\n");
        add("exchange.west_offer_surgeon.2.5.best",         "Best Regards,\\1Nurse J. Palmer");

        add("exchange.another_surgeon.object",              "Hire surgeon");
        add("exchange.another_surgeon.0.0.all",             "Dear all,\\n");
        add("exchange.another_surgeon.0.0.dr",              "Dear Dr. West,\\n");
        add("exchange.another_surgeon.0.0.nurse",           "Dear Nurse Palmer,\\n");
        add("exchange.another_surgeon.0.1.remarkable",      "Your previous surgeon has proved itself a remarkable assistant.\\n");
        add("exchange.another_surgeon.0.1.acceptable",      "Your previous surgeon's performance was acceptable.\\n");
        add("exchange.another_surgeon.0.1.longer",          "Your previous surgeon is no longer with us.\\n");
        add("exchange.another_surgeon.0.2.additional",      "I would like to hire an additional one. You will find enclosed the usual fee of five diamonds.\\n");
        add("exchange.another_surgeon.0.3.best",            "Best Regards,\\1");
        add("exchange.another_surgeon.0.3.faithfully",      "Faithfully,\\1");
        add("exchange.another_surgeon.0.4.name",            "%s");
        add("exchange.another_surgeon.0.4.dr_name",         "Dr. %s");
        add("exchange.another_surgeon.0.4.seeker",          "The Seeker of Truth");
        add("exchange.another_surgeon.1.0.dear",            "Dear Dr. %s,\\n");
        add("exchange.another_surgeon.1.1.thank",           "Thank you for your payment. You may find the surgeon larva enclosed.\\n");
        add("exchange.another_surgeon.1.2.best",            "Best Regards,\\1");
        add("exchange.another_surgeon.1.3.nurse",           "Nurse J. Palmer");

        add("exchange.more_surgeons.object",                "Hire surgeon");
        add("exchange.more_surgeons.0.0.all",               "Dear all,\\n");
        add("exchange.more_surgeons.0.0.dr",                "Dear Dr. West,\\n");
        add("exchange.more_surgeons.0.0.nurse",             "Dear Nurse Palmer,\\n");
        add("exchange.more_surgeons.0.1.additional",        "I would like to hire an additional surgeon. You will find enclosed the usual fee of five diamonds.\\n");
        add("exchange.more_surgeons.0.2.best",              "Best Regards,\\1");
        add("exchange.more_surgeons.0.2.faithfully",        "Faithfully,\\1");
        add("exchange.more_surgeons.0.3.name",              "%s");
        add("exchange.more_surgeons.0.3.dr_name",           "Dr. %s");
        add("exchange.more_surgeons.0.3.seeker",            "The Seeker of Truth");
        add("exchange.more_surgeons.1.0.dear",              "Dear Dr. %s,\\n");
        add("exchange.more_surgeons.1.1.thank",             "Thank you for your payment. You may find the surgeon larva enclosed.\\n");
        add("exchange.more_surgeons.1.2.best",              "Best Regards,\\1");
        add("exchange.more_surgeons.1.3.nurse",             "Nurse J. Palmer");

        add("exchange.ask_weeper.object",                   "Research directions");
        add("exchange.ask_weeper.0.0.dr",                   "Dear Dr. West,\\n");
        add("exchange.ask_weeper.0.0.all",                  "Dear all,\\n");
        add("exchange.ask_weeper.0.1.delve",                "As I delve deeper and deeper into the field, I am gaining a growing interest in your work on surgery and human anatomy.\\n");
        add("exchange.ask_weeper.0.1.performing",           "I have been performing several successful surgical experiments, and am now looking for new research directions.\\n");
        add("exchange.ask_weeper.0.1.grateful",             "I am grateful for your services provided so far, and wish to help out in whatever research efforts you may currently be tackling.\\n");
        add("exchange.ask_weeper.0.2.willing",              "Would you be willing to describe to me any of your current research goals? I would be able to put my resources to the task and help where practical.\\n");
        add("exchange.ask_weeper.0.2.unsolved",             "Do you know of any unsolved problems in the field that deserve practical inquiry? I have several subjects to test upon.\\n");
        add("exchange.ask_weeper.0.3.best",                 "Best Regards,\\1");
        add("exchange.ask_weeper.0.3.faithfully",           "Faithfully,\\1");
        add("exchange.ask_weeper.0.4.name",                 "%s");
        add("exchange.ask_weeper.0.4.dr_name",              "Dr. %s");
        add("exchange.ask_weeper.0.4.seeker",               "The Seeker of Truth");
        add("exchange.ask_weeper.1.0.dear",                 "Dear Dr. %s,\\n");
        add("exchange.ask_weeper.1.1.certainly",            "Certainly. As you clearly know, here at the Arkham Sanitarium we not only provide medical services, but are also involved in research efforts, and we are always glad to see interest in our niche field.\\n");
        add("exchange.ask_weeper.1.2.pupils",               "Some of my younger pupils have indeed made observations on the interaction between water and the human mind—we do not yet know why water displays such properties, but it appears capable of expanding a human's viewpoint beyond what the eyes can see. We thus began performing experiments, filling our patients' skulls with water until we deemed possible. This was a painful operation, requiring much sedative. To be clear, these were healthy patients, not lacking any internal organs—their spines and thus their bodies were their only bond with the physical world, while their mind would have been brought beyond it by water.\\n");
        add("exchange.ask_weeper.1.3.sadly",                "Sadly, our initial attempts were none too successful; a swollen head, and that was all. We believe we stopped too early, but unfortunately we do not currently have the manpower nor the test-subjects/volunteers needed to pursue further experiments.\\n");
        add("exchange.ask_weeper.1.4.cycles",               "If you have the cycles, we would be glad to see any successful experiments on your end, setting up for a collaboration. Please note that most of our findings are not suited for publication in traditional medical journals, and I would dissuade you from divulging this information.\\n");
        add("exchange.ask_weeper.1.5.best",                 "Best,\\1Dr. H. West");

    }

    private void addCommonCaptions() {
        add("caption.right_click", "Right Click");
        add("caption.shift_right_click", "Sneak + Right Click");
        add("caption.shift_right_click_lacrymatory", "Sneak + Right Click on Lacrymatory");
        add("caption.shift_right_click_air", "Sneak + Right Click in the air or water");
        add("caption.reminisce", "Hold %s");
        add("caption.crafting", "Go to crafting entry");
        add("caption.journal", "Go to journal entry");
        add("caption.research", "Go to research entry");
    }

    private void addDialogue() {
        add("dialogue.continue", "(Continue)");
        add("dialogue.end", "(End)");

        addDrownedDialogue();
        addMirrorDialogue();
        addShoremanDialogue();
        addBloodCultistDialogue();
        addShoremanCultistDialogue();
    }

    private void addShoremanCultistDialogue() {
        add("dialogue.shoreman_cultist.do_not.option", "The Keeper must die, but on his own terms. §oDo not§r take his life, or you too shall meet your end.");
        add("dialogue.shoreman_cultist.go_ahead.option", "If he must die, then it matters not in what manner – the dead do not regret. Go ahead.");
        add("dialogue.shoreman_cultist.0", "What?! What is this??");
        add("dialogue.shoreman_cultist.1", "Your friend betrays you. We wish to see the colour of your guts.");
        add("dialogue.shoreman_cultist.2", "No! I know I must die, but please, do not let me bleed like a pig!");
        add("dialogue.shoreman_cultist.3", "Ohhh, you wish you bled like a pig, yet your blood is older, and paler!");
        add("dialogue.shoreman_cultist.4", "You lie!");
        add("dialogue.shoreman_cultist.5", "You are the misbegotten child of earth and water. Tell us, §owas §oit §oyour §omother §oor §ofather §owho §owore §oscale §ofor §ocloth?");
        add("dialogue.shoreman_cultist.6", "||||[[[[....]]my mother[[[[....\n|||....]]]was §ohuman.");
        add("dialogue.shoreman_cultist.7", "Then Father Ocean shall embrace your remains.");
        add("dialogue.shoreman_cultist.do_not.0", "|||Just as I embraced life,||| so too shall I embrace death.");
        add("dialogue.shoreman_cultist.do_not.1", "NO!!!!!!!!");
        add("dialogue.shoreman_cultist.go_ahead.0", "NO!!!!!!!!");
        add("dialogue.shoreman_cultist.bastard", "THE BASTARD BLED WHITE!");
        add("dialogue.shoreman_cultist.laugh", "HAHAHA!||\nWell, friend – now you have breached the lie| – is that not what you wanted?");
    }

    private void addDrownedDialogue() {
        add("gui.dialogue.drowned.display_name", "Convergence");

        add("dialogue.drowned.gnawing.start.0", "For the first time, I notice it: something crawling at the back of my head.");
        add("dialogue.drowned.gnawing.start.1", "It is like a pain grown over several weeks or months, until you realize it's not normal and should not be there.");
        add("dialogue.drowned.gnawing.start.2", "Except... this one has been with me my whole life, in my mind and everyone else's. It is the chaos that crawls within all living beings.");
        add("dialogue.drowned.gnawing.i.option", "I shall not speak to it today.");

        add("dialogue.drowned.you.start.0", "There is complete silence.");
        add("dialogue.drowned.you.(end).option", "(End)");

        add("dialogue.drowned.ocean.human.0", "And so it is to be hateful.\nThe shoremen were punished for their devotion to the Great Dreamer, by your kin.");
        add("dialogue.drowned.ocean.start.0", "Hello, child.");
        add("dialogue.drowned.ocean.waterless.0", "I lie beneath all worlds, strewn across the stars.");
        add("dialogue.drowned.ocean.veil.0", "Delve into my depths, far below the earthly crust I bear atop of me.\nAnd let us speak again there.");
        add("dialogue.drowned.ocean.veil.1", "Be forewarned, however: I am now calm and placid, but I am multifaceted.\nAnd the next time we meet, I shall be tempestuous.");
        add("dialogue.drowned.ocean.child.0", "All life is born from me.");
        add("dialogue.drowned.ocean.who.0", "I am the Ocean, eldest in time.");
        add("dialogue.drowned.ocean.who.1", "I have also been called Arche, the First Principle, and Dagon, the Archpriest.");
        add("dialogue.drowned.ocean.depths.0", "Travel far from land, sailing on a canoe of the shoremen, holding a gift.\nMy oldest children will recognize you, and whisper secrets in your mind.");
        add("dialogue.drowned.ocean.last.0", "There was a convergence of thoughts.\nYou too are multifaceted, though you do not yet know, and your mind is fractured, irreparably.");
        add("dialogue.drowned.ocean.last.1", "And, of course, that Chaos that Crawls within all living beings, that Messenger who is the Message itself, spoke aloud, for the first time in your life.");
        add("dialogue.drowned.ocean.father.0", "The shoremen's dreams travelled through me, before leaving the world.\nAnd they dreamt grateful visions of you, of your compassion.");
        add("dialogue.drowned.ocean.dreamer.0", "No. I am His child.\nAnd through the Great Dreamer – the Veil itself – infinity is filtered into water, and from water all is born.");
        add("dialogue.drowned.ocean.are___.option", "Are you the Veil?");
        add("dialogue.drowned.ocean.how_.option", "How do I reach your depths?.");
        add("dialogue.drowned.ocean.it.option", "It is only human to be compassionate.");
        add("dialogue.drowned.ocean.are__.option", "Are you the Great Dreamer?");
        add("dialogue.drowned.ocean.one.option", "One last thing: who else entered my mind during my baptism?");
        add("dialogue.drowned.ocean.thank_.option", "Thank you, Father Ocean.");
        add("dialogue.drowned.ocean.how.option", "How do I reach the Veil?");
        add("dialogue.drowned.ocean.are_.option", "Are you the Veil?");
        add("dialogue.drowned.ocean.are_______.option", "Are you the Veil?");
        add("dialogue.drowned.ocean.are_________.option", "Are you the Veil?");
        add("dialogue.drowned.ocean.are____.option", "Are you the Great Dreamer?");
        add("dialogue.drowned.ocean.thank.option", "Thank you, Father Ocean.");
        add("dialogue.drowned.ocean.are______.option", "Are you the Great Dreamer?");
        add("dialogue.drowned.ocean.who.option", "Who are you?");
        add("dialogue.drowned.ocean.hello,.option", "Hello, Father Ocean.");
        add("dialogue.drowned.ocean.but.option", "But what about other worlds? Ones without water?");
        add("dialogue.drowned.ocean.am.option", "Am I your child?");
        add("dialogue.drowned.ocean.are_____.option", "Are you the Veil?");
        add("dialogue.drowned.ocean.are.option", "Are you the Great Dreamer?");
        add("dialogue.drowned.ocean.are________.option", "Are you the Great Dreamer?");


    }

    private void addBloodCultistDialogue() {
        add("gui.dialogue.blood_cultist.display_name", "Blood Cultist");

        add("dialogue.blood_cultist.initial.killed.0", "What worth is mercy when in the way of progress? Nothing – and the curiosity of ones like you – and us – is what drives the world forward.\nWe will meet once you have something to offer to – and something from demand from – our humble association.");
        add("dialogue.blood_cultist.initial.due_course.0", "All in due course.");
        add("dialogue.blood_cultist.initial.respawn.0", "Shhh, do not tell – we know already!  We must wait, and play the act anew – once you have something to demand from – and something to offer to – our humble association.");
        add("dialogue.blood_cultist.initial.start.0", "The chains binding us lie yet unseen – but worry not: all is linked, all is connected, and new life is born from each death – and you know death all too well, do you not?");
        add("dialogue.blood_cultist.initial.___.option", "...");
        add("dialogue.blood_cultist.initial.i_.option", "I resurrect from every death, if that is what you are asking.");
        add("dialogue.blood_cultist.initial.no,.option", "No, and I want nothing to do with you.");
        add("dialogue.blood_cultist.initial.i.option", "I have killed many, yes.");
        add("dialogue.blood_cultist.initial.psychopath___.option", "Psychopath...");
        add("dialogue.blood_cultist.initial.farewell,_.option", "Farewell, then.");
        add("dialogue.blood_cultist.initial.____.option", "...");
        add("dialogue.blood_cultist.initial.farewell,.option", "Farewell, then.");
        add("dialogue.blood_cultist.initial.farewell,__.option", "Farewell, then.");

        add("dialogue.blood_cultist.immortal.undying.0", "All the more compelling! You have been at both sides of the threshold, and oh if you could only remember what's on the other end of it!");
        add("dialogue.blood_cultist.immortal.forgetful.0", "We – nonetheless – see you as an important stepping stone in the long path of humankind – a fulcrum, perhaps – and a long, eventful life.");
        add("dialogue.blood_cultist.immortal.exception.0", "Saint Adelina, of course – Jerome's virtuous consort, who was said to have lived a thousand lives – she is immortal, just like you.");
        add("dialogue.blood_cultist.immortal.start.0", "Extraordinary – you are immortal.");
        add("dialogue.blood_cultist.immortal.hypocrisy.0", "Oh, drop the hypocrisy! You rip hearts and spines, experiment on hapless fools – you are in no place to make such noble statements, white knight – preach not what you do not follow.");
        add("dialogue.blood_cultist.immortal.tell.0", "Our proposed partnership is a business one – we trade in unorthodox goods, that may be of great help to your research.\nWhat we offer is §ohuman lives§r, folks plucked from their villages and left in your hands – to do with them what you desire.");
        add("dialogue.blood_cultist.immortal.answer.0", "In our letter we spoke of a partnership – we share a mutual interest – and though we seldom make offers to those outside our aggregation – for safety, of course – you have caught our attention like no one before – bar, perhaps, a single exception.");
        add("dialogue.blood_cultist.immortal.embrace.0", "I shall embrace death – just as I did life.\nNevertheless, know that – on behalf of my brothers and sisters – I come in peace, and with an offer.");
        add("dialogue.blood_cultist.immortal.horrible.0", "You are now playing in the economy of lives, dear – just like we are – and for the betterment of all humanity, it must continue.\nIf you wish to proceed, simply build the two pillars. We will be there to celebrate the beginning of our partnership.");
        add("dialogue.blood_cultist.immortal.study.0", "You have struck us, yes – undying as you are – like no one has ever done before – bar, perhaps, a single exception.");
        add("dialogue.blood_cultist.immortal.offer.0", "We are an aggregation of scholars – and merchants – bound by a common fascination of the links chaining us all – brother and sister – man and beast – life and death.");
        add("dialogue.blood_cultist.immortal.offer.1", "We wish to understand these relations – they are born and manifested in blood – and have devoted ourselves to studying – just as you have – so that we may understand that insurmountable threshold that is death.");
        add("dialogue.blood_cultist.immortal.offer.2", "§o* The cultist's voice changes slightly at every pause *");
        add("dialogue.blood_cultist.immortal.deceiver.0", "A worthy sacrifice – that did not come to pass – and in so daring, we proved your true nature – the blood coursing through your veins.");
        add("dialogue.blood_cultist.immortal.demand.0", "We are fascinated by the relation between life and death, between birth and passing – and what we ask in return is a §onewborn§r.\nIt does not have to be a human – any animal, from cat to horse, will do fine. Build two pillars: any baby born near to the first will signal us to bring a human near to the second.");
        add("dialogue.blood_cultist.immortal.demand.1", "That, then, is our trade. A newly born life in exchange for one soon to expire – at your hands, if you choose to.");
        add("dialogue.blood_cultist.immortal.hm-hm__.option", "Hm-hm. Alright, tell me what you want.");
        add("dialogue.blood_cultist.immortal.and_.option", "And I am quite certain you are not.");
        add("dialogue.blood_cultist.immortal.thanks,.option", "Thanks, I guess, but please tell me what you want.");
        add("dialogue.blood_cultist.immortal.then.option", "Then tell me, what do you demand in return?");
        add("dialogue.blood_cultist.immortal.§odeceiver!§r.option", "§oDeceiver!§r Your experiment could have costed a life!");
        add("dialogue.blood_cultist.immortal.tell.option", "Tell me why you have come here.");
        add("dialogue.blood_cultist.immortal.this.option", "This is all so horrible.");
        add("dialogue.blood_cultist.immortal.who's_.option", "Who's the exception?");
        add("dialogue.blood_cultist.immortal.§oyou.option", "§oYou just killed me!§r");
        add("dialogue.blood_cultist.immortal.and__.option", "And what do you demand in return?");
        add("dialogue.blood_cultist.immortal.§omadman_§r.option", "§oMadman.§r It will be your blood staining the ground.");
        add("dialogue.blood_cultist.immortal.i'm.option", "I'm not \"undying\". I just come back after death.");
        add("dialogue.blood_cultist.immortal.hm-hm____.option", "Hm-hm. Alright, tell me what you want.");
        add("dialogue.blood_cultist.immortal.i.option", "I hope it's worth the blood you just spilled.");
        add("dialogue.blood_cultist.immortal.who's.option", "Who's the exception?");
        add("dialogue.blood_cultist.immortal.and.option", "And you wish to study §ome§r?");
        add("dialogue.blood_cultist.immortal.§othat's§r.option", "§oThat's§r your trade? It is horrifying.");
        add("dialogue.blood_cultist.immortal.no.option", "No way.");
        add("dialogue.blood_cultist.immortal.alas,.option", "Alas, I'm forgetful. But you are right, I also wish I could.");
        add("dialogue.blood_cultist.immortal.you.option", "You have not answered my question.");
        add("dialogue.blood_cultist.immortal.i'll.option", "I'll think about it.");
        add("dialogue.blood_cultist.immortal.hm-hm_.option", "Hm-hm. Alright, tell me.");
        add("dialogue.blood_cultist.immortal.hm-hm___.option", "Hm-hm. Alright, tell me what you want.");

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
        add("dialogue.black_mirror.rationalize.jerome.0", "Jerome is a warrior turned saint. Though tormented by regret, he still took lives – he knew it had to be done. For the greater good.");
        add("dialogue.black_mirror.rationalize.morons.0", "Massively influential morons, then. They shaped the modern cultural zeitgeist into one embracing nihilism, or worse, cosmicism. No one will decry the sacrifice of individual lives.");
        add("dialogue.black_mirror.rationalize.no_amount.0", "Oh, great start. Want to try again?");
        add("dialogue.black_mirror.rationalize.great_minds.0", "What do the great minds of history have to say?");
        add("dialogue.black_mirror.rationalize.need.0", "Animals die all the time. Horribly.");
        add("dialogue.black_mirror.rationalize.randolph.0", "Before his disappearance in recent years, Dr. Carter espoused his pessimistic views on the insignificance of human lives. To him, it would not have mattered.");
        add("dialogue.black_mirror.rationalize.adelina.0", "Adelina does not hide her belief in sacrifice for the greater good. Interestingly, she is said to have lived multiple lives, and that in the last few years she has been tormented by with regret.");
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

        add("dialogue.black_mirror.rationalize2.start.0", "So?");
        add("dialogue.black_mirror.rationalize2.i.option", "I must still think about this.");
        add("dialogue.black_mirror.rationalize2.in.option", "In truth, I do not have a choice. But I will treat my patients with dignity, as their names enter the annals of history.");

        add("dialogue.black_mirror.rationalize3.start.0", "The decision is made.");
        add("dialogue.black_mirror.rationalize3.and.option", "And there's no coming back.");

        add("dialogue.black_mirror.after_weep.waste.0", "Why so? When quiet introspection can teach so much, what need is there to talk to others?");
        add("dialogue.black_mirror.after_weep.start.0", "There is just me here. The small fortress that is my mind.");
        add("dialogue.black_mirror.after_weep.others.0", "Yes. Of course. The plight of the shoremen should not remain unheard, and so too my story shall one day be told. Humanity is built on the sharing of thought.");
        add("dialogue.black_mirror.after_weep.others.1", "Only... it is exhausting. I should not forget to retreat into my own mental abode from time to time.");
        add("dialogue.black_mirror.after_weep.easy.0", "Easy answer. I must choose.");
        add("dialogue.black_mirror.after_weep.self.0", "Ah, of course! Rise then, oh socially inept! Our time has come!");
        add("dialogue.black_mirror.after_weep.self.1", "(But has it really? Will it ever?)");
        add("dialogue.black_mirror.after_weep.of.option", "Of course it has. The greatest thinkers worked alone.");
        add("dialogue.black_mirror.after_weep.am.option", "Am I wasting time, here, talking to myself?");
        add("dialogue.black_mirror.after_weep.nothing.option", "Nothing to do here");
        add("dialogue.black_mirror.after_weep.no_.option", "No. Nobody should claim to have all the answers: only by learning from others can we understand the full breadth of humanity.");
        add("dialogue.black_mirror.after_weep.as.option", "As is common, truth lies in the middle. A balance is needed.");
        add("dialogue.black_mirror.after_weep.absolutely_.option", "Absolutely. The peace and quiet of one's own thoughts is the truest bliss.");
        add("dialogue.black_mirror.after_weep.no__.option", "No. Civilization is built on unity – we cannot retreat into our own thoughts forever.");
        add("dialogue.black_mirror.after_weep.but.option", "But not too often, lest I really start talking to myself.");
        add("dialogue.black_mirror.after_weep.it.option", "It won't matter. Deep down, we cannot change who we are.");
        add("dialogue.black_mirror.after_weep.no___.option", "No. Nobody should claim to have all the answers: only by learning from others can we understand the full breadth of humanity.");
        add("dialogue.black_mirror.after_weep.indeed!.option", "Indeed! Only by reflecting on one's own self can one begin to understand what makes us who we are.");
        add("dialogue.black_mirror.after_weep.indeed!_.option", "Indeed! Only by reflecting on one's own self can one begin to understand what makes us who we are.");

        add("dialogue.black_mirror.idle.start.0", "There is just me here. The small fortress that is my mind.");
        add("dialogue.black_mirror.idle.my.option", "My cozy stronghold.");

    }

    private void addShoremanDialogue() {
        addShoremanLighthouseKeeperDialogue();
        addShoremanScholarDialogue();

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

        add("dialogue.shoreman_fisherman.communed1.start.0", "You... are born from water, just like us!\nYes, we all are! And that is the common ancestor that truly matters!");
        add("dialogue.shoreman_fisherman.communed1.i.option", "I hope you can see me as a friend from now on.");
        add("dialogue.shoreman_fisherman.communed1.through.option", "Through the Great Dreamer I see the world in a new light. I thank you for the revelation.");

        add("dialogue.shoreman_fisherman.communed2.jerome.0", "Heh, maybe. For all his misdeeds, he had a great mind.\nBut you, friend, have both that and kindness.");
        add("dialogue.shoreman_fisherman.communed2.start.0", "Bless! Bless! A blessing upon thee!");
        add("dialogue.shoreman_fisherman.communed2.citing.option", "Citing Jerome?");
        add("dialogue.shoreman_fisherman.communed2.thank.option", "Thank you. A blessing upon thee as well.");
        add("dialogue.shoreman_fisherman.communed2.bless.option", "Bless you as well.");
        add("dialogue.shoreman_fisherman.communed2.his.option", "His spouse, Adelina, was the truly vicious one.");


        //add("dialogue.shoreman_scholar.initial.start.0", "Oh? Good day, dear pilgrim. Are you in search of something, here in our humble dwelling?");
        //add("dialogue.shoreman_scholar.initial.knowledge.option", "Knowledge.");
        //add("dialogue.shoreman_scholar.initial.brought.option", "No. I was brought here by a dream.");
        //add("dialogue.shoreman_scholar.initial.suspicion.option", "Someone who does not view me with the stern eyes of suspicion.");
        //add("dialogue.shoreman_scholar.initial.start.0", "Oh? Good day, dear pilgrim. Are you in search of something, here in our humble dwelling?");

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

        add("dialogue.shoreman_clerk.communed1.start.0", "Hmph. Perhaps I was wrong about you.\nThen again, perhaps not. Perhaps you will betray us, and the good keeper who now so trusts you.");
        add("dialogue.shoreman_clerk.communed1.whatever.0", "Whatever you say.");
        add("dialogue.shoreman_clerk.communed1.lay.0", "I... I...\n...");
        add("dialogue.shoreman_clerk.communed1.what.0", "...\nNothing. Just... memories.");
        add("dialogue.shoreman_clerk.communed1.outside.0", "Yes, of course. Just do what the book tells you to do, and if we are in the way, so be it. We are irrelevant to you – just like you, perhaps, are irrelevant to someone else.");
        add("dialogue.shoreman_clerk.communed1.whatever2.0", "Whatever you say.");
        add("dialogue.shoreman_clerk.communed1.let's_.option", "Let's just trade.");
        add("dialogue.shoreman_clerk.communed1.yes_.option", "Yes. What *I* say.");
        add("dialogue.shoreman_clerk.communed1.farewell_.option", "Farewell.");
        add("dialogue.shoreman_clerk.communed1.what?.option", "What?");
        add("dialogue.shoreman_clerk.communed1.i_.option", "I would not dare breach your trust, nor lay a hand on any of you.");
        add("dialogue.shoreman_clerk.communed1.i.option", "I am not irrelevant. I am immortal.");
        add("dialogue.shoreman_clerk.communed1.___.option", "...");
        add("dialogue.shoreman_clerk.communed1.the.option", "The book doesn't tell me what to do. I tell *it* what I do.");
        add("dialogue.shoreman_clerk.communed1.let's.option", "Let's just trade.");
        add("dialogue.shoreman_clerk.communed1.let's__.option", "Let's just trade.");
        add("dialogue.shoreman_clerk.communed1.i__.option", "I can make no promises – it is outside of my control.");

        add("dialogue.shoreman_clerk.communed2.start.0", "Hello, traveller.");
        add("dialogue.shoreman_clerk.communed2.farewell_.option", "Farewell.");
        add("dialogue.shoreman_clerk.communed2.let's.option", "Let's trade.");


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

        add("dialogue.shoreman_carpenter.communed1.start.0", "Hmm. The Ocean spoke to you.\nAnd if you need to reply, you must sail it. Our canoes engender kindness from the sea: I shall share with you our woodwork.");
        add("dialogue.shoreman_carpenter.communed1.thank.option", "Thank you. I would be happy to see it.");
        add("dialogue.shoreman_carpenter.communed1.thank_.option", "Thank you. I may need it soon.");

        add("dialogue.shoreman_carpenter.communed2.start.0", "Carpentry's a hard job. Not like fishing – the Ocean brings me no gifts.");
        add("dialogue.shoreman_carpenter.communed2.farewell_.option", "Farewell.");
        add("dialogue.shoreman_carpenter.communed2.let's.option", "Let's trade.");

        add("dialogue.shoreman_carpenter.communed3.start.0", "Greetings.");
        add("dialogue.shoreman_carpenter.communed3.farewell_.option", "Farewell.");
        add("dialogue.shoreman_carpenter.communed3.let's.option", "Let's trade.");


        add("dialogue.shoreman_bartender.initial.start.0", "Hmph. We do not take kindly to outsiders, but I won't deny you a night's rest.\nThere are beds up above.");
        add("dialogue.shoreman_bartender.initial.anything.option", "Anything for sale?");
        add("dialogue.shoreman_bartender.initial.thank.option", "Thank you.");

        add("dialogue.shoreman_bartender.communed1.dreamer.0", "I... cannot pretend that I know for sure. It is a belief we are taught from youth, but what is a belief standing only on faith?");
        add("dialogue.shoreman_bartender.communed1.dreamer.1", "Many of my brethren now disagree that the Dreamer is aware of us at all – it is a matter of opinion – but we §oknow§r he exists, as sure as the nightly fog, the ignorance of man, and the starry sky dividing us from infinity!");
        add("dialogue.shoreman_bartender.communed1.start.0", "Aha! You heard it too, they say! Father Ocean's rumble and grumble and, through Him, the Great Dreamer's gaze! Only one outsider before has ever had such honour, so you're making history!");
        add("dialogue.shoreman_bartender.communed1.outsider.0", "A desert man, but he... he was not kind.\nI hope for our friendship to be stronger.");
        add("dialogue.shoreman_bartender.communed1.it.option", "It shall be.");
        add("dialogue.shoreman_bartender.communed1.it_.option", "It is an honour indeed. Cheers to that!");
        add("dialogue.shoreman_bartender.communed1.so.option", "So the Dreamer was really aware of me?");
        add("dialogue.shoreman_bartender.communed1.even.option", "Even within such a small community, you still have and accept a divide of thought. Surprising, yet beautiful.");
        add("dialogue.shoreman_bartender.communed1.i_.option", "I hope so too.");
        add("dialogue.shoreman_bartender.communed1.yes,.option", "Yes, he does listen. If he didn't, would that not make us irrelevant?");
        add("dialogue.shoreman_bartender.communed1.who.option", "Who was this outsider?");
        add("dialogue.shoreman_bartender.communed1.i.option", "I see. At least the Ocean listens.");
        add("dialogue.shoreman_bartender.communed1.of.option", "Of course, and let's drink to that!");

        add("dialogue.shoreman_bartender.communed2.start.0", "Welcome back, traveller.");
        add("dialogue.shoreman_bartender.communed2.farewell_.option", "Farewell.");
        add("dialogue.shoreman_bartender.communed2.show.option", "Show me your drinks.");


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

        add("dialogue.shoreman_drunk.communed1.loq.0", "Less what-cious?");
        add("dialogue.shoreman_drunk.communed1.ocean.0", "Ach, sea water, thar's ye problem! Away!");
        add("dialogue.shoreman_drunk.communed1.start.0", "Hmm. Smell diffe'ent.");
        add("dialogue.shoreman_drunk.communed1.some.option", "Some water would do you well.");
        add("dialogue.shoreman_drunk.communed1.was.option", "Was touched by the Ocean, it seems.");
        add("dialogue.shoreman_drunk.communed1.you.option", "You are less loquacious than last time.");
        add("dialogue.shoreman_drunk.communed1.clearly.option", "Clearly reaching a breaking point with the booze...");

        add("dialogue.shoreman_drunk.communed2.water.0", "Aiii!");
        add("dialogue.shoreman_drunk.communed2.start.0", "*beurk*");
        add("dialogue.shoreman_drunk.communed2.can.option", "Can I offer you some water?");
        add("dialogue.shoreman_drunk.communed2.your.option", "Your only weakness...");
        add("dialogue.shoreman_drunk.communed2.*beurk*.option", "*beurk* indeed.");

    }

    private void addShoremanScholarDialogue() {

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

        add("dialogue.shoreman_scholar.communed1.start.0", "Traveller... you have my gratitude.\nAnd not just that: I feel an innate admiration towards you, and your willingness to overcome those inner biases that gnaw at the mind of every human.");
        add("dialogue.shoreman_scholar.communed1.start.1", "You challenged your own beliefs in pursuit of what is true. That takes tenacity, as few humans would remain unperturbed by the sudden upheaving of their view of the world.");
        add("dialogue.shoreman_scholar.communed1.start.2", "Please, let me help you in any way I can. I am knowledgeable – perhaps my only asset in this world, and my only role in this quest of yours.\nCome back to me if you wish to discuss esoteric topics – such as those studied by the academia of the occult, but of which the outside world is blissfully unaware; or the beliefs to which my kin so tightly held even in the face of tragedy.");
        add("dialogue.shoreman_scholar.communed1.thank.option", "Thank you. I will come back.");

        add("dialogue.shoreman_scholar.discuss.grieving.0", "Or regretting her crimes.");
        add("dialogue.shoreman_scholar.discuss.baptism.0", "Each dialogue with the Ocean is a sacred act for us of the Shore, and for the first such occurrence we undergo a secret ritual: a baptism, biting into a slug while in water... until we drown.\nIt is not something we readily share with outsiders, yet, perhaps, as you embrace more and more of our customs, we will reveal it.");
        add("dialogue.shoreman_scholar.discuss.whom.0", "Our friends.");
        add("dialogue.shoreman_scholar.discuss.inhabited.0", "And how! All manners of creatures, thinking and not, some of which even rose to the surface and... er...");
        add("dialogue.shoreman_scholar.discuss.scholar.0", "I did my time in academia, long ago. My research lay at the intersection between cultural studies and theology: I investigated the interactions between radically different communities, particularly my own with all others, and put a special focus in the role of faith in such contrasts.");
        add("dialogue.shoreman_scholar.discuss.scholar.1", "I even held a professorship at the Miskatonic for a time, though I was denied tenure. Eventually I no longer felt welcome, and the ethics department started branding my research as \"unethical\". Before I reached my thirty-fifth year of age, the inquisitions began, and all was lost.");
        add("dialogue.shoreman_scholar.discuss.deadkeeper.0", "Do not speak of him.");
        add("dialogue.shoreman_scholar.discuss.disappeared.0", "Yes, and from there comes my warning: be afraid of death. You never know when it might be your last.");
        add("dialogue.shoreman_scholar.discuss.onnecronomicon.0", "Ah, right. You mentioned it.");
        add("dialogue.shoreman_scholar.discuss.blame.0", "Thank you. You, alone, have shown compassion. We cannot be grateful enough.");
        add("dialogue.shoreman_scholar.discuss.change.0", "Nothing. Just that... people change, over the course of long lives.");
        add("dialogue.shoreman_scholar.discuss.preconceived.0", "Yes. Perhaps one day we shall find a race from another world, and we shall compare our sciences, and we shall learn whether there is one true, objective science, or if even that is unreachable.");
        add("dialogue.shoreman_scholar.discuss.badthing.0", "Perhaps not indeed, and as you come to terms with the impossibility of it all, you may find yourself comforted in the knowledge that it does not truly matter, and that what is most important is your own perspective.");
        add("dialogue.shoreman_scholar.discuss.discomfit.0", "I can see that too, and how it could prove... frightening. This is the Veil after all, and to know that there is something out there, but that we cannot know what... it is what drives people mad.");
        add("dialogue.shoreman_scholar.discuss.listen.0", "Whether He listens or not, I cannot say – nor do I know what \"listening\" would even entail for something such as Him – but as for responding... no. He does not.\nAfter all, why would he? To Him we are naught but dust. He is... indifferent.");
        add("dialogue.shoreman_scholar.discuss.against.0", "Yet, what if you had been different back then?");
        add("dialogue.shoreman_scholar.discuss.gods.0", "Ah, but already we diverge: we believe in a god, not in gods.\nAnd, even then, I would hold qualms with referring to the Great Dreamer as a \"god\" – correlating Him to the mythical deities pervading your culture fails to capture the intricacies of our faith: we worship the ideal the Veil encapsulates, and the personification in our central square serves only to give the fishermen a feeling of tangibility, for something that is inherently intangible.");
        add("dialogue.shoreman_scholar.discuss.late.0", "I am afraid so. I am afraid §oto§r.\nBut, with you at my side, I can yet share my knowledge, and that comforts me enough.");
        add("dialogue.shoreman_scholar.discuss.death.0", "I know – for one such as you it would be easy to dismiss it.\nBut you should fear it nonetheless.");
        add("dialogue.shoreman_scholar.discuss.trust.0", "What do you mean \"is true\"?! How would I know if it is true or not!");
        add("dialogue.shoreman_scholar.discuss.veil2.0", "The Veil is all things that conceal a truth, of any sort.\nThe Veil is the curtain of a theatre, divorcing stage from audience.\nThe Veil is prejudice, and mankind's unwillingness to peek beyond it.");
        add("dialogue.shoreman_scholar.discuss.veil2.1", "The Veil is language, and the words we speak, unable to capture the true essence of things.\nThe Veil is your skin, your face, your persona, and everything concealing your true self.\nThe Veil is death, that final threshold through which we cannot see.");
        add("dialogue.shoreman_scholar.discuss.veil2.2", "The Veil is a great many things, and it resides deep down below, at the bottom of the sea. It is the great bulwark concealing infinity, the truth to all answers.\nWe call Him the Great Dreamer, for only dreams may pass through Him.");
        add("dialogue.shoreman_scholar.discuss.nowhere.0", "You keep surprising me – all these things, coming to your mind, as if from nowhere.\nThis symbol, however, is a terrible one: whereas the Eye of Knowledge symbolises mankind's freedom to seek their path to knowledge, this, the §oTendril of Knowledge§r, reflects a much bleaker philosophy.");
        add("dialogue.shoreman_scholar.discuss.nowhere.1", "It was adopted by a fringe movement among occult researchers – a sect within a sect – who dispute the notion of free will as applied to us Seekers of Truth, proclaiming instead that our journey towards knowledge is guided by some external force, as if snatched by a tendril against our consent.");
        add("dialogue.shoreman_scholar.discuss.nowhere.2", "I advise you not to draw that symbol. The movement was deemed heretic long ago, and even now the debate over its legitimacy remains heated. Blood was shed, and I would not want to lose you.");
        add("dialogue.shoreman_scholar.discuss.snippet.0", "A snippet of truth, and so the Veil tears a little.");
        add("dialogue.shoreman_scholar.discuss.life.0", "Er... yes. Except the life inhabiting those very depths, of course. They predate blood.");
        add("dialogue.shoreman_scholar.discuss.shoremen.0", "I see. We have a short but vivid history, one we wish the outside world would learn.");
        add("dialogue.shoreman_scholar.discuss.maths.0", "Indeed. And I hope that it never crumbles, and that we never find two axioms to simply... contradict each other.");
        add("dialogue.shoreman_scholar.discuss.repliednotalone.0", "Hah, that's wonderf-...\n...what?");
        add("dialogue.shoreman_scholar.discuss.frightened.0", "That is the nature of dealing with all things involving the Veil, and as we uncover more truths, so too do we understand how little we know.");
        add("dialogue.shoreman_scholar.discuss.alright.0", "Yes, yes... it's just that...\n...\nWell, know this: your ancestors travelled to those depths, to §ofight§r.");
        add("dialogue.shoreman_scholar.discuss.noitdidnt.0", "...");
        add("dialogue.shoreman_scholar.discuss.acquainted.0", "But... but I §oam§r acquainted with Al Azif! Though virtually unknown outside of occult academia, it is widely celebrated within our inner circles as the Madman of the Desert's magnum opus, a treasure trove of his arcane findings.");
        add("dialogue.shoreman_scholar.discuss.already.0", "Oh! Oh that is wonderful!\nAnd... and He replied?");
        add("dialogue.shoreman_scholar.discuss.stubborn.0", "Haha! Then perhaps stubbornness has become a virtue!");
        add("dialogue.shoreman_scholar.discuss.throughdreams.0", "Then perhaps our dreams embraced each other, dancing unseen in their own little world.");
        add("dialogue.shoreman_scholar.discuss.true.0", "This is the only true answer: one cannot judge how they would have acted when in a different state of mind, and like the sleepy man should not cancel plans for the next day, so too should the safe man not guess his actions in a perilous situation.");
        add("dialogue.shoreman_scholar.discuss.speak.0", "Yes, and, perhaps, He may even answer. However, be forewarned: the Ocean is multifaceted, and though He may appear calm and placid on one occasion, He may well be raging and thunderous on the next – do not seek to understand Him; He is beyond us.");
        add("dialogue.shoreman_scholar.discuss.anyone.0", "We are no longer that optimistic.");
        add("dialogue.shoreman_scholar.discuss.onalazif.0", "You drew it... on §oAl Azif§r?");
        add("dialogue.shoreman_scholar.discuss.yelling.0", "I'm sorry! I'm sorry, sorry, sorry! It's that... oh, what have you gotten yourself into...");
        add("dialogue.shoreman_scholar.discuss.twomore.0", "This... this is unprecedented. I truly do not know what to say...\n...but what did §othey§r say? When they spoke to you? Who were they??");
        add("dialogue.shoreman_scholar.discuss.afraid.0", "I do not blame you. Fear is the strongest emotion of mankind, and we know it all too well.");
        add("dialogue.shoreman_scholar.discuss.hadyoubeenthere.0", "I see. And do you know what you would have done, had you been there?");
        add("dialogue.shoreman_scholar.discuss.notsay.0", "Do not worry – I understand any reticence in sharing. I understand it very well.");
        add("dialogue.shoreman_scholar.discuss.reach.0", "You must descend the vessels of the world, the very veins bringing detritus down below – for the chain tying life and water is that red fluid you call blood.");
        add("dialogue.shoreman_scholar.discuss.ocean.0", "The Ocean is our medium: a Veil before the Veil, this immense bulwark concealing the void. It is through water that our dreams travel down, and so the Ocean forwards our prayers to the Dreamer.\nHe is, thus, our Archpriest, whom we also call Dagon, and who rules the depths for He §ois§r the depths.");
        add("dialogue.shoreman_scholar.discuss.ocean.1", "He is also our Father, senior to the stars and eldest in the universe, who spawned all life from water. Academia refers to Him as Arche, that is, the §oFirst Principle§r, whose turmoils originated the world.\nAnd so he has been an object of study in all of mankind's history: first, by philosophers; then, by warlocks; now, even by scientists.");
        add("dialogue.shoreman_scholar.discuss.you.0", "Yes, that too is astonishing... oh, what have you gotten yourself into...");
        add("dialogue.shoreman_scholar.discuss.convergence.0", "This is not good.\nThe Gnawing Sensation? The Chaos that Crawls? Had you ever heard those names before the baptism?! Answer me!");
        add("dialogue.shoreman_scholar.discuss.greater.0", "But then, even discounting the infeasibility of it all, and the intractable effort of amassing all such knowledge, and then absorbing it, even so, would it not still be marred by that final, inescapable bias that is your own, as you read and learn through your own lens, link it to your own events, and react to it as your temperament dictates? Even now as I speak.\nObjectivity is unattainable, and all knowledge is inherently subjective.");
        add("dialogue.shoreman_scholar.discuss.eye.0", "The Eye of Knowledge. It succinctly encapsulates the birth of life: from the outermost unknown, to the void, to water, to blood, to you, the pupil in the middle, usually depicted askew to symbolise the degree of freedom that you have, however small, to study what surrounds you.");
        add("dialogue.shoreman_scholar.discuss.eye.1", "That is, of course, only my interpretation, from one pupil to another.\nI wonder, if I may ask, where did you first encounter this symbol?");
        add("dialogue.shoreman_scholar.discuss.plight.0", "Deleted from the annals, oh the shame on those who wrote them!\nYes, our people were slain by yours, and some of us are old enough to remember it. Crusades, inquisitions, persecutions, witch hunts – whatever you want to call them, we were the monsters they hunted. We... normal men.");
        add("dialogue.shoreman_scholar.discuss.plight.1", "So how can one blame us when we mistrust outsiders? Were a whole people to come and take your family, how could you not be suspicious of anyone from that same population!");
        add("dialogue.shoreman_scholar.discuss.sentience.0", "\"We\" is all living creatures, so confident in our role as kings of the universe that we believe to have exclusive claim on sentience. The Great Dreamer is sentient, that is certain... but he does not §olive§r. He is more than that.");
        add("dialogue.shoreman_scholar.discuss.sorry.0", "Thank you.");
        add("dialogue.shoreman_scholar.discuss.immortal.0", "We saw it in our dreams. We knew that, had you not come in peace, we would have had no escape – even if we killed you.\nAnd we remember, for we had faced one such as you before.");
        add("dialogue.shoreman_scholar.discuss.chains.0", "Blood rituals are a staple practice among many peoples of the world, often involving sacrifice of mortal men and women to obtain boons. It has also been studied by academia, though not extensively, and always at a safe distance.");
        add("dialogue.shoreman_scholar.discuss.chains.1", "These rituals are now viewed by researchers as rigidly scientific processes, yet dressed in mysticism and the beliefs of those who practice them. I do not know – nor wish to know – the details – but, if you do, I suggest you contact the responsible department at the Miskatonic.");
        add("dialogue.shoreman_scholar.discuss.history.0", "Ohh, I wish it were that straightforward, and that one could simply prove things false or true, yet the real world teems with discrepancies and truths untold, and everything you can learn will brim chock-full with details that are not known, and that shall never be known, to be left inconsistent, incoherent, contradictory, for the history of the world is written not in prose,|| but in poetry.");
        add("dialogue.shoreman_scholar.discuss.neverfear.0", "Thank you.");
        add("dialogue.shoreman_scholar.discuss.noreply.0", "I see, but do not worry! You do indeed seem changed, and if you survived the ritual, then, certainly, it must have worked.\nI thus welcome thee among us once more, dear traveller; and let the Ocean's blessing be upon thee.");
        add("dialogue.shoreman_scholar.discuss.alazifwhat.0", "Hah, for a moment I thought you had scribbled your findings on the original!\nThough, yes, if you ever seek to publish it, I suggest you change its name.");
        add("dialogue.shoreman_scholar.discuss.science.0", "Hmm, perhaps you are right. At least within the frame of our world, and even despite the flaws of academia – of which, I assure you, there are several – science has the greatest prospect of being \"true\". Though it may depend on the science.\nYou know, I have always had a fascination towards mathematics: queen among sciences, boldly confident in the truth of its theorems and corollaries.");
        add("dialogue.shoreman_scholar.discuss.thankyou.0", "Thank you.");
        add("dialogue.shoreman_scholar.discuss.keeper.0", "At the top of the lighthouse, he can see everything.\nHe is the town elder, wisest among us, and the only one who still remembers the old ways. Everyone seeks his counsel.");
        add("dialogue.shoreman_scholar.discuss.foryou.0", "Yes.");
        add("dialogue.shoreman_scholar.discuss.whatelse.0", "Is there anything else you wish to speak about?");
        add("dialogue.shoreman_scholar.discuss.surgery.0", "...then you would find blood and guts. Do not go on that terrible path, I beg of you.");
        add("dialogue.shoreman_scholar.discuss.cannotknow.0", "I know that I cannot know – and that very fact proves my statement.");
        add("dialogue.shoreman_scholar.discuss.old.0", "However long Father Ocean lets him.");
        add("dialogue.shoreman_scholar.discuss.necronomicon.0", "That is... a morbid title, yet strangely alluring.");
        add("dialogue.shoreman_scholar.discuss.apples.0", "Ah, the good ones! And where were these good apples when the others came for us?!");
        add("dialogue.shoreman_scholar.discuss.noremember.0", "Like a dream. I understand.\nI shall enquire into any similar occurrences, among the shoremen and the world at large, and shall let you know of any findings. You did well to tell me.");
        add("dialogue.shoreman_scholar.discuss.conceit.0", "It is a matter of opinion, but, yes, I think it is. Indeed, I find it our greatest conceit that we insist on giving ones such as Him shape and form. On what grounds can we reduce Him to our stature?");
        add("dialogue.shoreman_scholar.discuss.never.0", "Then it was really... really Him... oh, what have you gotten yourself into...");
        add("dialogue.shoreman_scholar.discuss.start.0", "Esteemed friend, shall we meet and debate the state of humankind? Please, let me share all that I know.");
        add("dialogue.shoreman_scholar.discuss.symbols.0", "Symbols and emblems are widespread among researchers of the occult, often identifying one's affiliation and school of thought. Are there any you are interested in discussing?");
        add("dialogue.shoreman_scholar.discuss.tendril.0", "Huh? Where did you see this?");
        add("dialogue.shoreman_scholar.discuss.pray.0", "I guess... I guess we do it for ourselves. Like all worldly religions, in truth. To strengthen our belief in the only thing we know: that we cannot know.\nIt is a comforting feeling, that of recognising our limits, the small island that is the human mind.");
        add("dialogue.shoreman_scholar.discuss.pray.1", "And, of course, though the Veil remains silent, the Ocean does not – and to Him, and to His children, we do speak.");
        add("dialogue.shoreman_scholar.discuss.accept.0", "I am not surprised. He still holds a lot of love in his heart, despite all he's gone through.");
        add("dialogue.shoreman_scholar.discuss.groundtruth.0", "Does there have to be? How can you seek a single truth when every living thing has its own perspective of the universe? There are as many truths as there are beating hearts, else the world would not be so divided.");
        add("dialogue.shoreman_scholar.discuss.adelina.0", "Adelina, who has lived a thousand lives. Deemed a \"Saint\" like her betrothed, yet ruthless, cold-blooded – after all, a single life means so little to her.");
        add("dialogue.shoreman_scholar.discuss.understand.0", "Neither do I. I shall enquire into any similar occurrences, among the shoremen and the world at large, and shall let you know of any findings. You did well to tell me.");
        add("dialogue.shoreman_scholar.discuss.veil.0", "One could start pondering the ways in which a given nomenclature alters the frame through which we envision things, and how referring to a \"Dreamer\" may make the object more human and sympathetic than an abstract \"Veil\" – but yes, essentially they are one and the same.");
        add("dialogue.shoreman_scholar.discuss.slugs.0", "Slugs are little reminders of what life is like at the bottom of the sea; amorphous, inchoate, mirroring the turmoils of currents in those depths.\nThey are our means of communion, through which we speak to the ocean.");
        add("dialogue.shoreman_scholar.discuss.inperson.0", "I do not know of anyone who has, though I am sure some must have tried.\nBut then again, to what end? Nothing but dreams can breach it, and, even if you did venture into the deepest abyss, you would gaze disappointedly towards that last door, with no key in hand.");
        add("dialogue.shoreman_scholar.discuss.repliedalone.0", "Then you too heard His voice, soothing, consoling, the crashing of waves upon your mind, as you drowned... and then breathed.\nI thus welcome thee among us once more, dear traveller; and let the Ocean's blessing be upon thee.");
        add("dialogue.shoreman_scholar.discuss.ourselves.0", "Hah, good question, as proven by the fact that I do not know the answer. But that is a question for someone wiser than me, for knowing one's self is within the scope of wisdom, and not erudition.");
        add("dialogue.shoreman_scholar.discuss.i____________.option", "I drew it myself on the pages of the Necronomicon. I don't recall seeing it previously.");
        add("dialogue.shoreman_scholar.discuss.that.option", "That too.");
        add("dialogue.shoreman_scholar.discuss.i_.option", "I am sorry.");
        add("dialogue.shoreman_scholar.discuss.forgive.option", "Forgive me, but I'd rather not say.");
        add("dialogue.shoreman_scholar.discuss.you.option", "You are right. I cannot say.");
        add("dialogue.shoreman_scholar.discuss.what____.option", "What role did the lighthouse keeper have?");
        add("dialogue.shoreman_scholar.discuss.what_______.option", "What role did the lighthouse keeper have?");
        add("dialogue.shoreman_scholar.discuss.who's.option", "Who's \"we\"?");
        add("dialogue.shoreman_scholar.discuss.let___.option", "Let us talk another time.");
        add("dialogue.shoreman_scholar.discuss.always_.option", "Always.");
        add("dialogue.shoreman_scholar.discuss.i_____________________________.option", "I understand you have suffered much, but your plight is never mentioned in our history books. What can you tell me about it?");
        add("dialogue.shoreman_scholar.discuss.i_________________________________.option", "I do not remember.");
        add("dialogue.shoreman_scholar.discuss.no:.option", "No: I am the one who should be grateful to you, for opening my mind beyond the dogmas I used to believe in.");
        add("dialogue.shoreman_scholar.discuss.i_____________________________________.option", "I wish to talk about you shoremen.");
        add("dialogue.shoreman_scholar.discuss.but____.option", "But just a little.");
        add("dialogue.shoreman_scholar.discuss.this.option", "This was enlightening. Thank you.");
        add("dialogue.shoreman_scholar.discuss.hah!_.option", "Hah! Of course.");
        add("dialogue.shoreman_scholar.discuss.then_.option", "Then each such truth is part of one greater truth, and were each being to describe its own view, its own history, the course of its life, then we would finally see it all as a whole, unmarred by bias: all sides of a war, all angles of an event, all joy and all pain.");
        add("dialogue.shoreman_scholar.discuss.thanks_.option", "Thanks.");
        add("dialogue.shoreman_scholar.discuss.yes,.option", "Yes, but He wasn't alone.");
        add("dialogue.shoreman_scholar.discuss.does.option", "Does this mean He responds to your prayers? That He listens to us?");
        add("dialogue.shoreman_scholar.discuss.wait___.option", "Wait... what??");
        add("dialogue.shoreman_scholar.discuss.blissfully.option", "Blissfully unaware, is what I have been! Only recently did I see through the old dogmas.");
        add("dialogue.shoreman_scholar.discuss.then.option", "Then I shall reach it through dreams.");
        add("dialogue.shoreman_scholar.discuss.indeed_.option", "Indeed. Thank you for the enlightening conversation.");
        add("dialogue.shoreman_scholar.discuss.it_.option", "It is what anyone should have done.");
        add("dialogue.shoreman_scholar.discuss.dagon's_.option", "Dagon's blessing upon you as well.");
        add("dialogue.shoreman_scholar.discuss.it.option", "It should not have happened. None of it.");
        add("dialogue.shoreman_scholar.discuss.i______________________.option", "I... am frightened.");
        add("dialogue.shoreman_scholar.discuss.i_______.option", "I shall try nonetheless.");
        add("dialogue.shoreman_scholar.discuss.there.option", "There is a Veil within each and every one of us, as you said, and if I were to dig deep into our own bodies...");
        add("dialogue.shoreman_scholar.discuss.what__.option", "What is the Ocean to you?");
        add("dialogue.shoreman_scholar.discuss.can.option", "Can I speak to Him?");
        add("dialogue.shoreman_scholar.discuss.you____.option", "You couldn't be acquainted with it: it is the diary and journal upon which I note down my findings.");
        add("dialogue.shoreman_scholar.discuss.to.option", "To fight whom?");
        add("dialogue.shoreman_scholar.discuss.you__________.option", "You are right. The real truth is impenetrable.");
        add("dialogue.shoreman_scholar.discuss.i___________________________________.option", "I wish to talk about gods.");
        add("dialogue.shoreman_scholar.discuss.love.option", "Love is the hallmark of a good leader. You are fortunate.");
        add("dialogue.shoreman_scholar.discuss.indeed__.option", "Indeed.");
        add("dialogue.shoreman_scholar.discuss.what_________.option", "What does this tentacle represent?");
        add("dialogue.shoreman_scholar.discuss.i________________________________.option", "I wish to talk about you shoremen.");
        add("dialogue.shoreman_scholar.discuss.he.option", "He is old. How long can he still live?");
        add("dialogue.shoreman_scholar.discuss.if.option", "If I seek to learn the history of the world, I want my sources to be reliable. Would you read a book that was proven false?");
        add("dialogue.shoreman_scholar.discuss.for.option", "For good reasons.");
        add("dialogue.shoreman_scholar.discuss.and_.option", "And I wish to ask about you in particular. How do you know so much?");
        add("dialogue.shoreman_scholar.discuss.i__________________.option", "I... do not know.");
        add("dialogue.shoreman_scholar.discuss.i_______________________________________.option", "I don't understand.");
        add("dialogue.shoreman_scholar.discuss.let__.option", "Let us talk another time.");
        add("dialogue.shoreman_scholar.discuss.perhaps.option", "Perhaps I should.");
        add("dialogue.shoreman_scholar.discuss.so.option", "So it has.");
        add("dialogue.shoreman_scholar.discuss.indeed.option", "Indeed he was the first to accept me.");
        add("dialogue.shoreman_scholar.discuss.thank______.option", "Thank you.");
        add("dialogue.shoreman_scholar.discuss.only.option", "Only if I have to.");
        add("dialogue.shoreman_scholar.discuss.i____________________.option", "I hope so too.");
        add("dialogue.shoreman_scholar.discuss.and__.option", "And what is this Veil, then?");
        add("dialogue.shoreman_scholar.discuss.no__.option", "No. Never.");
        add("dialogue.shoreman_scholar.discuss.how_.option", "How do I talk to Him?");
        add("dialogue.shoreman_scholar.discuss.then______.option", "Then again, even science is founded upon our preconceived notions of the world.");
        add("dialogue.shoreman_scholar.discuss.you_______.option", "You are right. Maths is beautiful.");
        add("dialogue.shoreman_scholar.discuss.i___________________________.option", "I am sorry to hear.");
        add("dialogue.shoreman_scholar.discuss.nowhere,.option", "Nowhere, actually. It came to me right now.");
        add("dialogue.shoreman_scholar.discuss.i________________.option", "I would have fought against them.");
        add("dialogue.shoreman_scholar.discuss.what.option", "What do you mean?");
        add("dialogue.shoreman_scholar.discuss.that_.option", "That could never happen.");
        add("dialogue.shoreman_scholar.discuss.i___.option", "I... I must have heard the title somewhere and forgot, then.");
        add("dialogue.shoreman_scholar.discuss.do.option", "Do you find it wrong to personify Him?");
        add("dialogue.shoreman_scholar.discuss.but_.option", "But we may yet breach it.");
        add("dialogue.shoreman_scholar.discuss.i__.option", "I think I'll keep it for the time being.");
        add("dialogue.shoreman_scholar.discuss.you_________.option", "You paint a lovely picture.");
        add("dialogue.shoreman_scholar.discuss.immortal___.option", "Immortal... and still she disappeared.");
        add("dialogue.shoreman_scholar.discuss.it___.option", "It was indeed through dreams that I was brought to you.");
        add("dialogue.shoreman_scholar.discuss.i_________________.option", "I would have been too afraid, I admit.");
        add("dialogue.shoreman_scholar.discuss.i_____________________.option", "I don't understand.");
        add("dialogue.shoreman_scholar.discuss.it__.option", "It had to be done.");
        add("dialogue.shoreman_scholar.discuss.thank__.option", "Thank you.");
        add("dialogue.shoreman_scholar.discuss.let_.option", "Let us change subject.");
        add("dialogue.shoreman_scholar.discuss.i________________________.option", "I see.");
        add("dialogue.shoreman_scholar.discuss.thank____.option", "Thank you. I shall remain cautious.");
        add("dialogue.shoreman_scholar.discuss.i_________.option", "I am sorry...");
        add("dialogue.shoreman_scholar.discuss.just.option", "Just standing by. I understand.");
        add("dialogue.shoreman_scholar.discuss.science.option", "Science is objective. All (respectable) scientific knowledge is proven, by logic or experiment.");
        add("dialogue.shoreman_scholar.discuss.how__.option", "How do I reach His depths?");
        add("dialogue.shoreman_scholar.discuss.why.option", "Why are you yelling?!");
        add("dialogue.shoreman_scholar.discuss.i_______________________________.option", "I wish to talk about symbols.");
        add("dialogue.shoreman_scholar.discuss.perhaps___.option", "Perhaps... but she was never found. She may still be alive, grieving her beloved.");
        add("dialogue.shoreman_scholar.discuss.i____.option", "I think I'll rename it... §oNecronomicon§r.");
        add("dialogue.shoreman_scholar.discuss.who?.option", "Who?");
        add("dialogue.shoreman_scholar.discuss.i________.option", "I find this \"Gnawing Sensation\" to be not as relevant as a literal voice telling me it was... §omyself§r.");
        add("dialogue.shoreman_scholar.discuss.i___________.option", "I drew it myself on the pages of Al Azif. I don't recall seeing it previously.");
        add("dialogue.shoreman_scholar.discuss.i.option", "I shall not commit the same mistakes.");
        add("dialogue.shoreman_scholar.discuss.but.option", "But even then, there is a veil. Do we truly know ourselves?");
        add("dialogue.shoreman_scholar.discuss.i_______________.option", "I do not think her capable of regret.");
        add("dialogue.shoreman_scholar.discuss.i_____________.option", "I understand.");
        add("dialogue.shoreman_scholar.discuss.changed.option", "Changed the title, as you advised.");
        add("dialogue.shoreman_scholar.discuss.you_____.option", "You are right. I cannot blame you.");
        add("dialogue.shoreman_scholar.discuss.i______________.option", "I see; but, perhaps, that is not a bad thing.");
        add("dialogue.shoreman_scholar.discuss.then____.option", "Then it adequately mirrors its content.");
        add("dialogue.shoreman_scholar.discuss.i__________________________________.option", "I... am frightened.");
        add("dialogue.shoreman_scholar.discuss.two.option", "Two more voices in my head, but I did not talk back.");
        add("dialogue.shoreman_scholar.discuss.___.option", "...");
        add("dialogue.shoreman_scholar.discuss.i_____.option", "I find it discomfiting, frankly.");
        add("dialogue.shoreman_scholar.discuss.you_.option", "You will never have to fear me.");
        add("dialogue.shoreman_scholar.discuss.hah!.option", "Hah! I am quite the stubborn individual – I do not change easily.");
        add("dialogue.shoreman_scholar.discuss.i____________________________________.option", "I wish to talk about symbols.");
        add("dialogue.shoreman_scholar.discuss.what__________.option", "What do the slugs mean to you shoremen?");
        add("dialogue.shoreman_scholar.discuss.what?.option", "What? Are you alright?");
        add("dialogue.shoreman_scholar.discuss.what_____.option", "What is the Ocean to you?");
        add("dialogue.shoreman_scholar.discuss.one.option", "One claimed to be the Gnawing Sensation at the back of my mind. The other claimed to be... me?");
        add("dialogue.shoreman_scholar.discuss.i________________________________________.option", "I don't understand.");
        add("dialogue.shoreman_scholar.discuss.you___.option", "You... know I am immortal?");
        add("dialogue.shoreman_scholar.discuss.i___________________.option", "I shall try nonetheless.");
        add("dialogue.shoreman_scholar.discuss.arche.option", "Arche is inhabited?");
        add("dialogue.shoreman_scholar.discuss.i____________________________.option", "I understand you have suffered much, but your plight is never mentioned in our history books. What can you tell me about it?");
        add("dialogue.shoreman_scholar.discuss.i______.option", "I have already gone through it.");
        add("dialogue.shoreman_scholar.discuss.thank___.option", "Thank you.");
        add("dialogue.shoreman_scholar.discuss.i_______________________.option", "I do not fear death.");
        add("dialogue.shoreman_scholar.discuss.what___.option", "What role does the lighthouse keeper have?");
        add("dialogue.shoreman_scholar.discuss.let.option", "Let us change subject.");
        add("dialogue.shoreman_scholar.discuss.thank_.option", "Thank you. Maybe I shall.");
        add("dialogue.shoreman_scholar.discuss.but__.option", "But there has to be a \"ground truth\" at the bottom of it all! Something we can deconstruct, to map all facts and events as they happened.");
        add("dialogue.shoreman_scholar.discuss.let's.option", "Let's talk of something else.");
        add("dialogue.shoreman_scholar.discuss.no_.option", "No.");
        add("dialogue.shoreman_scholar.discuss.what________.option", "What do these concentric circles represent?");
        add("dialogue.shoreman_scholar.discuss.and.option", "And I wish to ask about you in particular. How do you know so much?");
        add("dialogue.shoreman_scholar.discuss.then_____.option", "Then let us hope for His blessing.");
        add("dialogue.shoreman_scholar.discuss.you__.option", "You cannot know that.");
        add("dialogue.shoreman_scholar.discuss.i_________________________.option", "I understand. Thank you.");
        add("dialogue.shoreman_scholar.discuss.you______.option", "You can never generalize from the actions of a few. There is conflict within every population, and just as many may have been for it as against.");
        add("dialogue.shoreman_scholar.discuss.these.option", "These §odeep creatures§r you refer to... did they fight for you?");
        add("dialogue.shoreman_scholar.discuss.yes_.option", "Yes.");
        add("dialogue.shoreman_scholar.discuss.i__________.option", "I... never thought it possible. I shall heed your warning. Thank you.");
        add("dialogue.shoreman_scholar.discuss.then___.option", "Then why pray at all?");
        add("dialogue.shoreman_scholar.discuss.thank_____.option", "Thank you.");
        add("dialogue.shoreman_scholar.discuss.this_.option", "This was enlightening. Thank you.");
        add("dialogue.shoreman_scholar.discuss.dagon's.option", "Dagon's blessing upon you as well.");
        add("dialogue.shoreman_scholar.discuss.you________.option", "You have nothing to fear.");
        add("dialogue.shoreman_scholar.discuss.i__________________________.option", "I see. That substance common to all life.");
        add("dialogue.shoreman_scholar.discuss.i______________________________.option", "I wish to talk about gods.");
        add("dialogue.shoreman_scholar.discuss.how___.option", "How can I trust that what you say is true?");
        add("dialogue.shoreman_scholar.discuss.is.option", "Is it too late to go back?");
        add("dialogue.shoreman_scholar.discuss.can_.option", "Can I reach the Veil, in person? Has anyone ever done it?");
        add("dialogue.shoreman_scholar.discuss.perhaps_.option", "Perhaps that day shall come sooner rather than later.");
        add("dialogue.shoreman_scholar.discuss.thank.option", "Thank you. I look forward to it.");
        add("dialogue.shoreman_scholar.discuss.what_.option", "What is the Ocean to you?");
        add("dialogue.shoreman_scholar.discuss.then__.option", "Then I shall reach it through dreams.");
        add("dialogue.shoreman_scholar.discuss.are.option", "Are the Great Dreamer and the Veil one and the same?");
        add("dialogue.shoreman_scholar.discuss.i______________________________________.option", "I understand.");
        add("dialogue.shoreman_scholar.discuss.what______.option", "What role does the lighthouse keeper have?");
        add("dialogue.shoreman_scholar.discuss.how.option", "How do I reach Him?");
        add("dialogue.shoreman_scholar.discuss.but___.option", "But how? Where can I find these chains?");


    }

    private void addShoremanLighthouseKeeperDialogue() {
        add("dialogue.shoreman_lighthouse_keeper.initial.start.0", "Oh. A traveller.||| \nWelcome.");
        add("dialogue.shoreman_lighthouse_keeper.initial.start.1", "How did you stumble upon our hamlet, may I ask? Was it chance? Or did you seek us?");
        add("dialogue.shoreman_lighthouse_keeper.initial.mere_chance.option", "Mere chance.");
        add("dialogue.shoreman_lighthouse_keeper.initial.hard_to_believe.option", "As hard as it is to believe, I was guided here by a dream.");
        add("dialogue.shoreman_lighthouse_keeper.initial.dream.0", "A... dream? A dream of what?");
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
        add("dialogue.shoreman_lighthouse_keeper.initial.not_aware.option", "I... was not aware. Our church speaks of \"sinners\", and of \"inhuman heretics\". I did not imagine..");
        add("dialogue.shoreman_lighthouse_keeper.initial.just_humans.0", "We are just §ohumans§r!  But §o§lyou§r were fearful of us venerating a different god, and all that he represents.\nBecause §owe§r worship the Veil, that great final threshold all dreams go through. The clouding of thoughts, the fog in the night, the blindness of man and the prejudice driving you towards unspeakable acts.");
        add("dialogue.shoreman_lighthouse_keeper.initial.just_humans.1", "And it lives deep down below, at the bottom of the sea. Hiding all sorts of truths, that filter of infinity in the emptiness of the §ovoid§r.\nYour ancestors could not tolerate its existence, because it would have shattered all your beliefs.");
        add("dialogue.shoreman_lighthouse_keeper.initial.not_forefathers.option", "I am not my forefathers.");
        add("dialogue.shoreman_lighthouse_keeper.initial.beg.0", "Then §oplease§r, I beg of you...\n§oLet no further harm come upon my people.§r");
        add("dialogue.shoreman_lighthouse_keeper.initial.no_fear.option", "You must not fear me.");
        add("dialogue.shoreman_lighthouse_keeper.initial.mankind.option", "My mission is for all of mankind, and that includes §oyou§r.");
        add("dialogue.shoreman_lighthouse_keeper.initial.groundless.option", "Your accusations are groundless. There are no records of any such \"crusades\". I cannot trust your words.");

        add("dialogue.shoreman_lighthouse_keeper.initial1.start.0", "§oPlease§r... I beg of you...\n§oLet no further harm come upon my people.§r");
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

        add("dialogue.shoreman_lighthouse_keeper.communed1.start.0", "Once the mind relinquishes its preconceptions, so too does the journey towards truth become lighter.\nDo not tarry, Seeker! You have so many wonders yet ahead of you.");
        add("dialogue.shoreman_lighthouse_keeper.communed1.fear.option", "Fear not. I have all the time I need.");
        add("dialogue.shoreman_lighthouse_keeper.communed1.thank.option", "Thank you... for opening my mind.");

        add("dialogue.shoreman_lighthouse_keeper.wantslug.old.0", "I am old, and do not have long left to live.\nBut I am not afraid, and in passing I know Father Ocean shall embrace my remains.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.start.0", "Welcome back, dear friend.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.slugs.0", "Oh, that is a joy to hear! They are, indeed, sacred to us.\nOnly, a warning: they are an acquired taste.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.survive.0", "Well... I hope so.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.variety.option", "Variety is the spice of life.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.___.option", "...");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.i.option", "I believe so too.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.i_.option", "I will survive.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.i__.option", "I would be glad to try one of your white slugs.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.and.option", "And I wish to embrace your culture. I would be glad to try one of your white slugs.");
        add("dialogue.shoreman_lighthouse_keeper.wantslug.just.option", "Just wanted to make sure you are doing well.");

        add("dialogue.shoreman_lighthouse_keeper.afterslug.old.0", "I am old, and do not have long left to live.\nBut I am not afraid, and in passing I know Father Ocean shall embrace my remains.");
        add("dialogue.shoreman_lighthouse_keeper.afterslug.start.0", "Welcome back, dear friend.");
        add("dialogue.shoreman_lighthouse_keeper.afterslug.i.option", "I believe so too.");
        add("dialogue.shoreman_lighthouse_keeper.afterslug.just.option", "Just wanted to make sure you are doing well.");

        add("dialogue.shoreman_lighthouse_keeper.baptism.outsider.0", "Indeed, and for precisely this reason I think you can make it.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.old.0", "I am old, and do not have long left to live.\nBut I am not afraid, and in passing I know Father Ocean shall embrace my remains.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.then.0", "And then you drown.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.different.0", "That...\nI do not know. Perhaps our attunement with water, or the strength of our faith. Things you now share.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.start.0", "Welcome back, dear friend.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.ocean.0", "Ah, I was afraid you would, eventually.\nBaptism is a sacred rite we undergo when young, to enter the next stage of our lives.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.ocean.1", "Yet, it is dangerous.\nListen: no outsider has ever attempted this, and if they did, they would not survive it.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.remember.0", "I was terribly, terribly afraid, and when I was put in the water, knowing only that I should not let go, I realized I could not breathe and that my life was running out and I knew for certain that they had lied to me because I could not breathe no matter how hard I tried and I was going to die and be forgotten and all my life had led to nothing and was worthless and then I hear Him and I BREATHE.\nIn, and out. In. And out. Again. And again.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.then2.0", "Hopefully, it will not all end there.\nYet, sometimes it does, and some of my brethren just let go, so that we never again heard their voice.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.tell.0", "Slugs are our means of communion. You must consume one, while submerged in water in a small hole surrounded by our dark sand.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.you.0", "Yes, as we all did.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.breathe.0", "Yes. And so will you, I hope.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.certainty.0", "§oListen,§r Seeker.\nWe are human, just like all of you – but like any two humans, we also differ, and there are things we cannot do that others can, and viceversa.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.voice.0", "Calm and placid.\nBut, be forewarned: he is not always so.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.i___.option", "I believe so too.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.what?!.option", "What?!");
        add("dialogue.shoreman_lighthouse_keeper.baptism.then_.option", "Then tell me. Please.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.just.option", "Just wanted to make sure you are doing well.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.intriguing___.option", "Intriguing...");
        add("dialogue.shoreman_lighthouse_keeper.baptism.you.option", "You do not know? Then why proclaim with such certainty that an outsider would die, when none even attempted?");
        add("dialogue.shoreman_lighthouse_keeper.baptism.i_____.option", "I understand. Thank you.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.i__.option", "I thought I was no longer an outsider to you.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.do.option", "Do you remember how it was?");
        add("dialogue.shoreman_lighthouse_keeper.baptism.have.option", "Have you gone through the ritual too?");
        add("dialogue.shoreman_lighthouse_keeper.baptism.i______.option", "I will keep that in mind. Thank you.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.what.option", "What makes you different from outsiders?");
        add("dialogue.shoreman_lighthouse_keeper.baptism.then.option", "Then tell me. Please.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.i____.option", "I wish to speak to the Ocean.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.how.option", "How was His voice?");
        add("dialogue.shoreman_lighthouse_keeper.baptism.i_.option", "I understand. Then please tell me how to perform the ritual, so that I may see whether we are more alike than not.");
        add("dialogue.shoreman_lighthouse_keeper.baptism.and.option", "And then?");
        add("dialogue.shoreman_lighthouse_keeper.baptism.you_.option", "You breathed in water?");
        add("dialogue.shoreman_lighthouse_keeper.baptism.i.option", "I hope so too. Thank you.");

        add("dialogue.shoreman_lighthouse_keeper.baptism2.start.0", "Welcome back, dear friend.\nI wish you the best for your baptism.");
        add("dialogue.shoreman_lighthouse_keeper.baptism2.thank.option", "Thank you.");

        add("dialogue.shoreman_lighthouse_keeper.baptism3.her.0", "Yes. But one day she vanished all the same.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.voices.0", "What?! That has never happened before! No, no, it cannot be!");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.ocean.0", "And blessed be Dagon, then! Embracing you like you embraced us.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.awakened.0", "I do not know either. Perhaps nobody does.\nSearch texts, ask luminaries, see if anyone went through the same.\nI pray for you, friend.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.start.0", "You live! Oh, blessings, bless, bless you!");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.adelina.0", "\"Saint\" Adelina. But one day she vanished all the same.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.children.0", "Oh... well, you are ready now.\nAnd I know your bravery, and that you will do so unafraid... but be prudent. Do not offend them.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.know.0", "We saw it in a dream, afraid that the past would repeat itself.\nIf you had not come in peace, how could we have stopped you? Just like we did not stop §oher§r.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.deepones.0", "They are our... friends. And allies.\nBut... no, words will not do them justice. You must see them yourself. They are wondrous, made in the shape of Father Dagon.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.immortal.0", "I know that you cannot truly die, Seeker. But you are not the first, and you will not be the last.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i_____.option", "I spoke with the Ocean.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i____.option", "I see. Thank you.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.then_.option", "Then I shall be prudent.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.adelina?.option", "Adelina?");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i___.option", "I shall be courteous, then.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i______.option", "I heard three voices.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i________.option", "I still spoke to the Ocean, though. He told me to meet his oldest children.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.thank.option", "Thank you.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i__.option", "I shall be courteous, then.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i_______.option", "I am also shaken. I do not know what I awakened.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.can.option", "Can you tell me what to expect?");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i_.option", "I do not think anyone could have gone through the same. I am... unique.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.he.option", "He was not alone. I heard three voices.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.then.option", "Then I shall be prudent.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.how.option", "How did you know?");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.he_.option", "He told me to meet his oldest children, sailing out on a canoe.");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.who.option", "Who else was like me?");
        add("dialogue.shoreman_lighthouse_keeper.baptism3.i.option", "I see. Thank you.");

    }

    private void addResearch() {
        add("research.FIRSTDREAMS.title", "The Language of Dreams");
        add("research.didDream.text", "I must sleep. I must Dream.");
        add("research.crafted_metal.text", "Create a memory of metal.");
        add("research.reminisced_metal.text", "Find a way to talk to the dream.");
        add("research.FIRSTDREAMS.stage.0", "It feels natural to dismiss dreams as a trick of the mind, when such dismissal could itself be the trick.\\nThe sensation of travelling to and from the Nether felt familiar, and yet I have no recollection of any similar journey in my life. It left me with the intense feeling of plunging down, gods know how far into stone and bedrock, only to then rise right back up. A feeling I have often experienced in dreams.\\nCould I be underestimating their import? Are they born from my daily sensations, or could they tell me something §omore§r, about the world and about myself?\\nThe desire to understand swells within me. Dreams are such simple, everyday events whose true nature I never bothered to investigate, like a shallow-looking puddle whose bottom I never chose to touch.\\nBut what if it hid an ocean?");
        add("research.FIRSTDREAMS.stage.1", "It felt wrong. I woke up shuddering, drenched in sweat. It was like diving into a pool from thousands of man-heights above, head first, eyes closed.\\n" +
                "Yet I can make no conclusions. Was this... §odream§r a simple reflection of my recent real life experiences, or did it have a life of its own?\\n" +
                "Oh, what a fascinating prospect! That of a sentient dream, living and breathing as I do. I wonder, then: could I talk to it? And if so, in what tongue?\\n" +
                "I propose to speak through my own §omemories§r: their effect on our dreams is undeniable. Most importantly, the study of human memories is a well-trodden field of research in academic circles. Past literature details the construction and usage of a memory sieve, a simple altar upon which I can place an item that evokes a certain memory, and subsequently store the result in a memory phial.\\n" +
                "I will begin with an iron ingot: an intense memory of metal, of the first time I mined a vein and saw wealth trickle in my hands. There is so much metal underground, could the dream see it?");
        add("research.FIRSTDREAMS.stage.2", "Replicating the steps of my predecessors was no issue. It is now my responsibility to tie it to the study of dreams.\\n" +
                "The goal is to have my own memory affect my mind while I sleep, and to do so I must experiment. A few different possibilities come to mind:\\n" +
                "[%s] Hold the phial in my hand while I sleep.\\1" +
                "[%s] Drink the phial's contents right before sleeping.\\1" +
                "[%s] (More complex) Build a fume spreader, place it next to my bed, fill it with oniric incense and then with the phial.\\n" +
                "Once I wake up, I {§nmust close my eyes and concentrate§r}[caption:reminisce], to try and reminisce what the dream saw.");
        add("research.FIRSTDREAMS.stage.3", "It worked. \\nAn outlandish hypothesis, a wild shot in the dark and yet it §ostruck§r something, because there §owas§r something to strike in that gloom escaping our senses.\\nToday I pioneer the Art of Dreaming, so that humankind may see the world in a new light, and elude the limits of our worldly bodies. And I §ovow§r to continue exploring and expanding this field as far as I can, here on the pages of Al Azif.");

        add("research.CRAFTING.title", "Crafting Registry");
        add("research.MEMORIES.title", "Memories");

        add("research.SLEEP_CHAMBER.title", "Sleep Chamber");
        add("research.slept_in_chamber.text", "Experiment until you can answer at least the first question.");
        add("research.SLEEP_CHAMBER.stage.0", "I had always been proud of my ability to stay awake and work and walk as long as I needed, leaving sleep to times of absolute necessity. Now, however, it is sleep I need, and I cannot get enough of it. My research studies are limited by the frequency at which I can dream. A most frustrating bound.\\n" +
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
                "Where lies the lie? Were my eyelids sewn shut at birth, dogmas drawn in their insides to see nothing else? Or am I too quick to dismiss the old adages, their wisdom shown in their persistence, in their supremacy over the heretical folks whom they... they...\\n" +
                "No. I must be rigorous. An empty dream, one I cannot reminisce, at the tip of my mind but barely outside its grasp. Yet my next dream might, and learn that it must slow down its descent, stay still within the Void, the Veil, the Dreamer.");
        add("research.EMPTY_DREAMS.stage.1", "It worked.");

        add("research.WATER_DREAMS.title", "Dreams of Water");
        add("research.reminisced_void_water.text", "Dream of Void, then Water.");
        add("research.WATER_DREAMS.epigraph", "§oThe deep sea\\0" +
                "Lies beneath all worlds,\\0" +
                "An ocean\\0" +
                "Strewn across the stars§r\\1");
        add("research.WATER_DREAMS.epigraph.source", "Old Shoreman saying\\n");
        add("research.WATER_DREAMS.stage.0", "Our mind stands atop the small, sinking isle of what it sees and feels, besieged on all fronts by the endless waves of the unknown.\\n" +
                "Now the tide rises, the illusion shatters, and what we thought we knew is submerged once more.\\n" +
                "Lies. Some perpetrated by millennia-old institutions, busy polishing unearned offerings; others encouraged by our own (perhaps merciful) minds, and the wishful thinking clouding our thoughts.\\n" +
                "But we know dreams can show us more than meets the eye. Fix our lapses in judgement, grant us a new perspective. Breach our preconceived notions, this veil obscuring the unknown.\\n" +
                "The shoremen were right. I must dream of Water, a memory so intense I may forget to breathe.\\n" +
                "But first I must bolster it with Void, to still its descent, and give myself time to... listen.\\n" +
                "...\\n" +
                "Merciful Gods, please,\\1" +
                "§odon't let me drown.");
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
        add("research.HEARTS.stage.1", "The heart still beats. When placed on the ground, the sound will attract nearby undead (is it some sort of nostalgia that drives them towards life?)\\n" +
                "The organ itself, however, may also be used for other purposes. The practice of blood rituals has been celebrated my many peoples throughout history, and has been extensively documented by academia. There is much more to be done here.");

        add("research.SPINES.title", "Willpower resides in the spine");
        add("research.extracted_spine.text", "Extract a spine, then release the patient.");
        add("research.SPINES.stage.0", "What makes a human stand tall and straight in the face of adversity, and to always rise from every fall?\\n" +
                "The parallels are clear, and the millennia-old syncretism of the studies of body and soul is precisely what I seek to delve into. To deprive a human of its spine – however sickening – may prove the link with its power of will. \\n" +
                "A spine extraction is a painful operation that requires an incision to be present.");
        add("research.SPINES.stage.1", "The convalescent can no longer walk, though this does not prove much. I sadly do not have a proper way – especially with a patient so crippled – to verify their lack or presence of willpower. Not yet, at least. However, the knowledge of how to extract a spine may prove a valuable resource, along with the spines themselves.");

        add("research.BONE_TIARA.title", "Bone Tiara");
        add("research.extracted_bone_tiara.text", "Craft a bone tiara.");
        add("research.BONE_TIARA.stage.0", "I picture a crown of bones, resting atop my head. A frightening sight: if I were to turn into the monster, would that make me fearless?\\n" +
                "Fortunately, I shall be turned in no true horror. The crown is simply a tiara, forged from the spine of a human. It is a back operation, requiring an incision and the insertion of three emerald gems. However – and very importantly – a dose of softener is needed before each insertion, due to the sharp edges of the gems. \\n" +
                "If all is done correctly, an extraction will produce the bone tiara.");
        add("research.BONE_TIARA.stage.1", "The tiara fills me with grit and certainty: as long as I wear it, I shall not back down.\\n" +
                "It turns me immune to various effects, including terror, mining fatigue, weakness, slowness, and more. Even knockback from attacks is now inconsequential to me.");

        add("research.WATERY_CRADLE.title", "The Watery Cradle");
        add("research.crafted_watery_cradle.text", "Craft a watery cradle.");
        add("research.first_skull_operation.text", "Inject either of the two hormones above in a patient.");
        add("research.WATERY_CRADLE.stage.0", "The mind lies deepest among the veils obscuring humanity from itself: that impenetrable mystery that, despite all miracles of modern science, has not yet been breached. My exploration of human anatomy should not stop at the worldly body, and must instead seek to explain the processes behind our very thoughts.\\n" +
                "The surgery bed is not appropriate for such operations: the patient must be lulled into a dreamlike state, unable to conceive or realise what is happening – and, once again, water is the key. I shall build a specialised chamber for this purpose, a \"watery cradle\" of sorts, to enable injections in the skull.");
        add("research.WATERY_CRADLE.stage.1", "The cradle is built, but requires testing. This is perhaps an opportunity to prove the lack of willpower in those without a spine.\\n" +
                "I will inject hormones in the patients' skulls. Of course, such substances are already known to alter the personality of an individual – perhaps busting that myth of free will, and so forth – yet the question remains on how intense their effects can become.\\n" +
                "I shall begin either with: (i) order hormones, making the patient obsessively aware of its surroundings and any items lying out and about; or (ii) parental hormones, intensifying the patient's motherly instincts and pushing it into breeding nearby animals. I will add further information in my journal.");
        add("research.WATERY_CRADLE.stage.2", "The successful operation proves – again – the fragility of the human mind. The watery cradle will become yet another tool in my research.");


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

        add("research.FARMING_TECHNIQUES.title", "Farming Techniques");
        add("research.used_farming_technique.text", "Test the technique.");
        add("research.FARMING_TECHNIQUES.stage.0", "Despite being a primarily maritime people, the shoremen also sporadically engage (or, at least, used to) in farming activities, particularly in those involving natural fibers critical to the production of rope and twine.\\n" +
                "Detached from other cultures using bone meal as fertilizer, the shoremen found their own technique to speed up crop growth, using a game of weights and balance. One must be wearing boots of diamond and leggings of gold, and hold dirt in their main hand. Then, one must jump on tilled soil; in so doing, the soil won’t be ruined, but, instead, the crop growing on it will be fertilized.");
        add("research.FARMING_TECHNIQUES.stage.1", "The technique worked – surprisingly, might I add, as I would have expected the armor’s added weight to be a drawback rather than a boon. It seems, however, that such weight, combined to my own, is just enough for the footprints left in the soil to be beneficial, rather than not.");

        add("research.DELICACIES.title", "Delicacies of the Shore");
        add("research.ate_delicacy.text", "Try a local dish.");
        add("research.DELICACIES.stage.0", "Although much of their food culture revolves around slugs, this seafood-centric cuisine of the shoremen has much more to offer, and through the mixing of various ingredients they can conjure tastes and textures that are much more palatable to outsiders. I have catalogued various recipes of theirs in these pages.");
        add("research.DELICACIES.stage.1", "These dishes may be quite unorthodox in their look and making, but, I must say, such uniqueness only adds to their exotic charm. Besides, they don’t taste half bad, and though they may not restore much hunger in the immediate, they tend to be relatively saturating in the long term.");

        add("research.CARPENTRY.title", "Carpentry");
        add("research.received_fish.text", "Sail for a while in a canoe.");
        add("research.CARPENTRY.stage.0", "The creaking wood and the damp textures of the Shoremen's dwellings belie the artistry involved in their construction. This is a folk who mastered the craft of building on water, and did so wholly for the sake of their religious beliefs.\\n" +
                "Though I don't expect to replicate their results, I may still be willing to imitate their style. Most of their building materials can be easily obtained.\\n" +
                "Perhaps more importantly, I wish to sail in one of their canoes, which they claim to be \"closer to the Ocean\".");
        add("research.CARPENTRY.stage.1", "Fish just started... jumping inside. They do not see the canoe or its rider as a threat, but rather a friend, an ally. \\n" +
                "It is as if Father Ocean himself was making me a gift.");

        add("research.GRASPING_WATER.title", "Grasping Water");
        add("research.grasped_water.text", "Take and place water in this manner.");
        add("research.GRASPING_WATER.stage.0", "The seafaring lifestyle of the shoremen led to them becoming proficient in handling water. Some of their techniques, however, appear otherworldly in nature, at least to us outsiders.\\n" +
                "The shoremen would close their eyes before trying to fill an empty bucket with water. In doing so, the water is removed from the ground, but the bucket remains empty.\\n" +
                "More importantly, any water taken in this manner may later be placed anywhere using an empty bucket, again with eyes closed.");
        add("research.GRASPING_WATER.stage.1", "The technique worked, yet I cannot grasp the trick. What happens in the world when I close my eyes?");

        add("research.BAPTISM.title", "Baptism");
        add("research.ready_for_baptism.text", "Learn at least three of the shoremen customs, then speak to the keeper.");
        add("research.baptized.text", "Perform the ritual.");
        add("research.BAPTISM.stage.0", "After having reached a certain stage of their life, shoremen appear to undergo a secret rite to speak with the Ocean. They are still tightlipped about its details, but as I embrace more and more of their customs they may become willing to reveal them.");
        add("research.BAPTISM.stage.1", "The keeper’s words were ominous, but I must proceed, just as I did for communion.\\n" +
                "I must dig a hole three blocks deep, but only one wide, open at the top and surrounded in dark sand elsewhere. I must fill it completely with §ostill§r water, for each of its three empty blocks.\\n" +
                "Then, I must sink to its bottom and devour a slug... and wait for what comes next.\\n" +
                "Merciful God, please,\\1" +
                "don’t let me drown.");
        add("research.BAPTISM.stage.2", "His voice was calm, placid, yet assertive, authoritative. His instructions were clear: I must make contact with His children, and so I shall.\\n" +
                "But... what else did I awaken? There was a convergence of thoughts, centered upon me.\\n" +
                "Oh, what did I do?");

        add("research.DREAM_BOTTLE.title", "Dream Bottle");
        add("research.used_bottle.text", "Use the dream bottle with some memories.");
        add("research.filled_bottle.text", ""); // this is secret
        add("research.DREAM_BOTTLE.stage.0", "Dreams hold great relevance among the shoremen, and it is unsurprising that some of their customs overlap with my own research. Of great interest is their \"Dream Bottle\", a sort of flask that would induce in them a dreamlike state – perhaps not dissimilar from that of my Sleep Chamber?\\n" +
                "If so, I could try to replicate the effects already observed in my studies by filling the bottle with memory phials before drinking its contents.\\n" +
                "Remarkably, the bottle itself is... unremarkable. Aside from a single diamond, its crafting involves only some clay, before filling it with water. Interestingly, the shoremen claim that, in the past, they would fill it with their own tears, for they had so much grief in them. Now their tears are spent, and I could never weep so much myself.");
        add("research.DREAM_BOTTLE.stage.1", "It works, and the bottle becomes yet another way for me to dream. It is easily portable and can allow me to utilize four memory pials at once. However, it can only be used once per day.");
        add("research.DREAM_BOTTLE.stage.2", "It works, and the bottle becomes yet another way for me to dream. It is easily portable and can allow me to utilize four memory phials at once.\\n" +
                "Normally, it can only be used once per day. However, filling it with tears overcomes this flaw, allowing me to use it as many times as I want as long as there is sufficient liquid.");

        add("research.LOCAL_MEDICINE.title", "Local Medicine");
        add("research.used_antidote.text", "Test a capsule.");
        add("research.LOCAL_MEDICINE.stage.0", "The shoremen show no shame for their belief in miracles – and, with all I’ve recently learned, who am I to judge?\\n" +
                "Nonetheless, miracles are hard to come by when time shows no quarter, and in light of emergencies such as bites from deep sea fauna, the shoremen have endeavoured to find solutions through natural ingredients.\\n" +
                "Their antidote capsules are now widespread in their communities, and are used to treat poison or wither effects. These remedies can either be used manually or set to trigger on infection. In so doing, they cure the negative effect and, most importantly, grant immunity for roughly a minute.");
        add("research.LOCAL_MEDICINE.stage.1", "The capsules work as expected. It’s tempting to see local medicine as backwards – and it so very often is – but one must not forget these antidotes came from lengthy, vicious trial and error. In a way, the process was rigidly scientific.");

        add("research.FIRST_CONTACT.title", "First Contact");
        add("research.had_contact.text", "Contact the Deep Ones, and throw them a human heart.");
        add("research.FIRST_CONTACT.stage.0", "Theory begets practice, and when the journey of the mind meets its limits, and dreams' feeble memories turn too hazy, so comes the time for the quest to become physical, diving in with flesh and bone.\\n" +
                "Dagon's command is rightful. I must commit my whole body to reach the Veil; yet, the Great Dreamer lies too far down below, occulted by Father Ocean's boundless presence. Instead, my first step shall be on the surface, atop of starlit water, mirror to the cosmos.\\n" +
                "His firstborn children are not the Shoremen, who, humans like any of us, are far down the lineage. Rather, he may have referred to the daemons described by the prospectors of Arche, foes to the old hunters such as Jerome and Adelina. These denizens of the depths – it is said – would sporadically rise to the surface, frightening to death some unfortunate sailors.\\n" +
                "Even Adelina spoke of them in hushed tones, but what could have frightened an immortal such as her? Unless...\\n" +
                "No. I must hold steadfast, and do as he commanded. With my canoe, I shall sail far into the deep ocean, until no land is in sight; there, in the dark of night, I will consume a slug. If his words are true, the ones from the deep shall arise, and contact will commence.  When it is asked of me, and only then, I shall offer a gift: the heart of a human, to seal our bond. \\n" +
                "During all of this, I must hold still in the canoe, and at no point will I leave it, not even if fear were to take me. My life is at stake.\\n" +
                "Then again, a single death is a small price to me. I always come back.\\n" +
                "Right?");
        add("research.FIRST_CONTACT.stage.1", "The encounter was peaceful. Or, at least, I believe so – no branch of human linguistics or semiotics could provide a definitive answer.\\n" +
                "Yet the deed is done and the bond is sealed. I must speak to Dagon once more, but this time I shall do so from within His very own essence.\\n" +
                "Is it time then? To retrace the steps of the old prospectors and hunters, and venture down into the abyss?\\n" +
                "To reach Arche, from where everything is born. \\n" +
                "The first principle of our world. ");

        add("research.BLOOD_RITUALS.title", "Blood Rituals");
        add("research.asked_thesis.text", "Send a letter to the Miskatonic.");
        add("research.received_thesis.text", "Wait for a response from the Miskatonic.");
        add("research.performed_ritual.text", "Perform one of the rituals.");
        add("research.BLOOD_RITUALS.stage.0", "I see the struggle in my patients' eyes, and it pains me to no end. How would §oI§r feel in their stead? Proudly knowing themselves to be §othe§r building block in the pathway of science, yet aware that they shall not live to see the outcome; it is the tragic destiny of all those who strive to a goal greater than themselves, unachievable in their lifetime. It comes as no surprise, then, that their enthusiasm wanes in those last, fateful moments, before they gift me their heart.\\n" +
                "It is only fair that I immortalise their sacrifice, so they may live beyond death. I must embrace their relinquished blood and put it to good use: indeed, rituals exist that exploit the red fluid to forge bindings. Could one then breach the Veil by linking the lie to the truth? The stage to the audience? Life to death?\\n" +
                "I must send a letter to the Institute of Anthropological Studies at the Miskatonic University in Arkham. They authored the seminal work §oSanguis: Inter Vitam et Mortem§r, that recounts the practice of blood rituals throughout the world; I know the work to be no longer in print, but I hope they may make an exception.");
        add("research.BLOOD_RITUALS.stage.1", "Georg was kind – I should treasure such connections.\\n" +
                "The thesis itself provides a wealth of knowledge. Though the rituals described are not meant for replication, it would be a disservice to science not to investigate them further. A few lives for the sake of all of humankind is a small price to pay, especially when those lives were so short-lived anyway.");
        add("research.BLOOD_RITUALS.stage.2", "It works! All the theatrics behind the decorated altar and solemn tone of the sacrifice may be nothing more than buffoonery at this point, but then again I do not know where the line between scientific phenomenon and traditional custom lies – I should thus adhere to the instructions as closely as possible.");

        add("research.BLOOD_WELL.title", "The Blood Well");
        add("research.built_well.text", "Build a Blood Well.");
        add("research.spawned_undead.text", "Spawn a blood undead.");
        add("research.BLOOD_WELL.stage.0", "When the great final threshold is irreversibly reached, once-living things sink in the ground, and their remains traverse the veins of the world until they arrive at their ancient progenitor: the waters of Arche.\\n" +
                "Blood Wells were built to access these veins directly, letting undead creatures enter them. The Well would attract such monsters – like a heart, but even stronger – and send them to a blood pool far down below, in the deepest ocean. Most importantly, such creatures would then be bound to the Well's maker, and may be summoned at their behest.\\n" +
                "I must construct one, in hopes of thinning the distance between me and Arche. I have recorded the required schematic; the heart must be placed §llast§r.");
        add("research.BLOOD_WELL.stage.1", "The Blood Well is born, and by the Dreamer, it §oreeks§r; a stench reminiscent of a rotting corpse – or a thousand.\\n" +
                "Nonetheless, I am certain the undead will not mind. In its current form, the Well should be able to embrace any zombies or skeletons, and send them far down below – though I expect most of them not to make it there intact.\\n" +
                "I should now create a Blood Orb. This will allow me to view and summon the creatures residing in my personal blood pool, and that arrived there safely.");
        add("research.BLOOD_WELL.stage.2", "The creature was covered in blood. It was... unrecognisable. \\n" +
                "The Well appears to be a powerful tool, adjacent to the necromantic arts, capable of building my own undead army. More work can be done in this area: a Blood Gem, also described in Mauer's thesis, can be used to summon multiple creatures at once.\\n" +
                "Additionally, creatures in my blood pool may be assigned to categories, for simpler management. Categories are defined by a triad of colours: I can throw dyes in the Well (one at a time), to add a colour to the right of the triad. For example, throwing blue dye, clay (symbolising colourless), and lime dye will make the assigned triad §lblue§r, §lnone§r, §llime§r. Throwing red afterwards will make the new triad §lnone§r, §llime§r, §lred§r. The default is §lnone§r, §lnone§r, §lnone§r.\\n" +
                "As useful as all of this is in terms of gaining power, the Well's true role in my quest is to get me closer to Arche. I should not forget that.");

        add("research.SURGEONS.title", "Surgeons");
        add("research.received_surgeon.text", "Hire a surgeon from the Sanitarium.");
        add("research.placed_surgeon.text", "Place the surgeon larva in the appropriate place.");
        add("research.grew_surgeon.text", "Grow the surgeon into an adult.");
        add("research.SURGEONS.stage.0", "Dr. West's letter comes at a good time. It might be appropriate to start thinking of delegating my work.");
        add("research.SURGEONS.stage.1", "Having reached adulthood, the surgeon is ready to operate. There are two important components involved in controlling the creature: (1) a surgeon bell, through which I can dictate the infrastructure that the surgeon is allowed to operate with, and (2) my own surgery notes, which will define the operations performed by it.\\n" +
                "§lSurgeon's bell§r\\n" +
                "The bell can be {§nbound to a surgeon§r}[caption:right_click] in order to check and edit the list of blocks it can interact with. While a surgeon is bound, I can {§nuse the bell§r}[caption:right_click] to tell it which surgical structure (e.g. a surgery bed) it can perform operations in. \\n" +
                "I can also {§nuse§r}[caption:right_click] the bell to declare inputs:\\n" +
                "- Use on a flask or flask shelf to add or remove it from the set of blocks it can draw liquids or solid ingredients from.\\n" +
                "- Use on a patient pod to add or remove it from the set of blocks it can take patients from.\\n" +
                "- Use on the top side of a block to add or remove it from the set of areas it can take patients from, e.g. collecting knocked out villagers from the ground.\\n" +
                "Finally, I can {§nuse§r}[caption:shift_right_click] it to declare outputs:\\n" +
                "- Use on a chest or other general container to add or remove it from the set of containers it can place outcome items in (such as extracted organs).\\n" +
                "- Use on a patient pod to add or remove it from the set of blocks it can put convalescent patients in.\\n" +
                "- Use on the top side of a block to add or remove it from the set of areas it can place convalescent patients in (though the surgeon will refrain from doing so if there are already too many convalescents in the area).\\n" +
                "§lSurgery Notes§r\\n" +
                "I can print down the notes from my journal on pieces of paper in my inventory. The surgeon will follow them exactly, only stopping if lacking ingredients or patients, or space to place the outcomes. It will, however, ignore any purely \"informational\" notes that do not describe an operation, such as lines indicating the occurrence of a complication, or additional data such as the operation's success. \\n" +
                "The surgeon will also ignore operations marked as incomplete, i.e. incomplete incisions, insertions, or extractions. It is thus good practice to avoid giving the surgeon any surgery notes containing incomplete operations, as otherwise the result may become unpredictable.\\n" +
                "The surgeon requires no tools.");

        add("research.WEEPERS.title", "Weepers");
        add("research.crafted_lacrymatory.text", "Craft a lacrymatory.");
        add("research.WEEPERS.stage.0", "The injection had cataclysmic effects, not just on the head but on the entire body. It is vestigial now, acting as the creature's only anchor to our world – other than that, the patient is no longer among us.\\n" +
                "Does he see more or less than we do? Did he cross the veil, or have I simply placed another veil between us? Oh, he is certainly fearful, lost to derealisation as he is – and he weeps, more than I ever could. Indeed, he can do nothing but weep.\\n" +
                "...\\n" +
                "Should... should I make the most out of this situation, and at least put his tears to good use? I know now that tears hold power, and the poor thing would not want its fate to have been in vain.\\n" +
                "If so, I can craft a lacrymatory, which shall hold all that he weeps.");
        add("research.WEEPERS.stage.1", "After picking him up, I can place the weeper next to the {§nlacrymatory§r}[caption:shift_right_click_lacrymatory], where he will deposit his tears. Eventually, there should be sufficiently many for me to take and use in other experiments.");

        add("research.FLETUM.title", "The Fletum");
        add("research.FLETUM.stage.0", "A weeper without its spine is nothing but its head. It is content, this way.\\n" +
                "Such a creature shall be called a Fletum. Fleti can be picked up and assigned to a lacrymatory like weepers, though they will produce fewer tears.\\n" +
                "However, their dreamlike state may and should be leveraged, and if I find a way to focus their dreams then I might, perhaps, see their effects in the real world.");

        add("research.BLOOD_FIST.title", "Blood Fist");
        add("research.BLOOD_FIST.stage.0", "A tool used to focus the powers bestowed by worldly bindings. It is created from the heart of a still-living patient: the insertions of a silk gland, a marrow gland and a gunpowder bladder in the chest of a patient will let the subsequent extraction produce the blood fist. No capacity is needed.\\n" +
                "These ingredients can be respectively obtained by killing spiders, skeletons, and creepers, all with the surgical precision of a scalpel.\\n" +
                "The usage of the fist itself depends on the binding, and may have different effects when wielded in the main or off-hand. It is likely related to the witch hands used in infusions by practitioners of witchcraft.\\n");

        add("research.NEW_DEPTHS.title", "Deep Down Below");
        add("research.entered_arche.text", "Create the Nautilus and enter Arche.");
        add("research.spoke_to_dagon.text", "Speak to Father Ocean.");
        add("research.NEW_DEPTHS.epigraph", "§oIn my conceit, I no longer cherished the lives of mortals; yet, face to face with the sublime, I saw my hypocrisy.\\0" +
                "The ocean predates the world, and so too will it outlive it.\\0" +
                "Even I am nought to eternity.§r\\1");
        add("research.NEW_DEPTHS.epigraph.source", "From §oReflections of an Immortal§r, published last year. In it, Adelina looks back to her journeys in Arche, three decades prior.\\n");
        add("research.NEW_DEPTHS.stage.0", "Thirty-seven years have gone by since the first expeditions to Arche began, and thirty-one since the last ones ended. Incited by both secular and religious bodies, they were justified as necessary, holy purges against \"abominable monsters\" threatening our peace  – the Deep Ones, clearly – yet I now see the truth of their origin: they were merely perpetuating the violence that had begun in the black shores, against this new enemy that had risen to the shoremen's protection. \\n" +
                "We shall continue their journey, but not their legacy: our mission will be one of peace, to mend bonds between humanity and Dagon.\\n" +
                "The old hunters' knowledge will come useful, however: fliers were affixed across towns, acting as instruction manuals for prospective explorers. We include here a copy of such a pamphlet, together with the schematics for building the recommended equipment.\\n" +
                "Importantly, some things shall differ from the old missions: first, owing to Mauer's kindness, we know how to create Vessel Stones via Blood Rituals, and thus won't have to procure any from official sources.\\n" +
                "Second, whereas a normal human would immediately collapse under the immense pressures of Arche's waters, I believe that, having undergone baptism, our body may withstand them for a short duration. Leaving the Nautilus may be possible (and sometimes necessary), though we should be careful not to do so for an extended period, and certainly not during a current.\\n" +
                "So: our goal is to speak to Dagon, but for now let's just enter His realm and gauge its dangers. It may be accessed via a blood well: just as life is pushed upwards through the arteries of the world, so too do deceased things descend through its veins. One must thus enter the well §owith rotten flesh in one hand and coal in the other§r, and so be transported down below. Just like the dead. Ahh, what an omen.");
        add("research.NEW_DEPTHS.stage.1", "The old hunters' quest converged on the cities inhabited by the Deep Ones, and so too shall ours. Unlike them, our search shall be guided by dreams of water: just as they bring us to an ocean monument when performed on the surface world, so will they show us the way to a deep city when performed in Arche. I... §oknow§r they will.\\n" +
                "If no hostilities arise with the city's denizens, we shall then head towards the colossal altar dominating the city, and there, §oat the very top§r, consume a slug to enter communion with Father Ocean once more. He will guide us to Truth.");
        add("research.NEW_DEPTHS.stage.2", "I do not understand. Such kindness and magnanimity when he first spoke to me in my baptism, replaced with vicious indifference in the maelstrom of the deepest Ocean.\\n" +
                "He spoke yet did not listen; to hear Dagon from within his own body... did it reveal its true nature, or just another facet of something entirely unpredictable, inscrutable by our scientific means, indescribable by our human criteria, unconstrained by our axioms?");

    }

    private void addCraftingRegistry() {
        add("research.CRAFTING.introduced", "First introduced in: {§2%1$s§r}[link:research.%2$s].");

        add(FUME_SPREADER.get().getDescriptionId() + ".crafting", "Breathing its fumes while asleep will alter the behaviour of my dreams.\\1Must be placed on the ground near a bed or similar object. It must then be filled with {§noniric incense§r}[link:crafting.oniric_incense] and, finally, with a {§nmemory phial§r}[link:crafting.memory_phial] containing the desired memory.\\1It can be {§nemptied§r}[caption:Shift + Right Click], but doing so will not return the memory phial.");
        add(ONIRIC_INCENSE.get().getDescriptionId() + ".crafting", "Used on an empty {§nfume spreader§r}[link:crafting.fume_spreader] to fill it.");
        add(MEMORY_PHIAL.get().getDescriptionId() + ".crafting", "A seminal discovery in the study of human memories. My contributions stand in its applications to dreams.\\1May be used on a {§nmemory sieve§r}[link:crafting.memory_sieve] to capture a memory.");
        add(MEMORY_PHIAL.get().getDescriptionId() + ".crafting2", "A seminal discovery in the study of human memories. My contributions stand in its applications to dreams.\\1May be used on a {§nmemory sieve§r}[link:crafting.memory_sieve] to capture a memory, and then on a full {§nfume spreader§r}[link:crafting.fume_spreader] to assign it the contained memory.");
        add(MEMORY_SIEVE.get().getDescriptionId() + ".crafting", "A fundamental device in the art of manipulating memories.\\1An item {§nevoking a certain memory§r}[caption:memories] must be placed on it. Then, an {§nempty phial§r}[link:crafting.memory_phial] may be used on it to capture the memory.");
        add(SLEEP_CHAMBER.get().getDescriptionId() + ".crafting", "A complex device known to mimic the dream state induced by sleeping.\\1To use it, I must open its door, walk inside and close it while enclosed within.");
        add(SEAWEED_STEW.get().getDescriptionId() + ".crafting", "A simple but honest recipe; it can be served both hot and cold. Seaweed itself is a staple of Shore cuisine.");
        add(KELP_COD_BUNDLE.get().getDescriptionId() + ".crafting", "Bread is not widespread in Shore cuisine, but these sorts of bundles are a common \"food on the go\" alternative. The different textures match better than expected.");
        add(TROPICAL_DELIGHT.get().getDescriptionId() + ".crafting", "Its festive look and hefty portions often accompany celebrations in Shore culture, making for a sophisticated recipe for special occasions. It is a very filling dish, prepared with exotic fish coupled with various herbs and spices.");
        add(ANTIDOTE_CAPSULE.get().getDescriptionId() + ".crafting", "Used to treat or prevent poison and wither effects. When affected by either ailment, the capsule will be applied automatically if held in the hotbar. It can also be used manually. Doing so will grant immunity for a certain length of time.");
        add(ALEMBICS.get().getDescriptionId() + ".crafting", "Via the mixing and matching of liquids and solids, this device can create something entirely new... yet analogies to the ancient waters of Arche would constitute foolish ambitions: it cannot create life.\\1" +
                "The first and leftmost alembic as well as the third one can store input fluids used in crafting. The second, small one can accept input items.\\1" +
                "A fluid flask must be placed to accept the output. If the input fluid, item and second fluid make for a valid recipe, the flask will slowly start getting filled.\\1" +
                "A given input item may be sufficient for a certain amount of output fluid. #GIVE EXAMPLE");
        add(ARCHE_DIAL.get().getDescriptionId() + ".crafting", "Arche’s currents are chaos spawning order, amorphous matter breeding life – and, lying at this intersection, does it surprise us or not that they act with such predictable patterns?\\1" +
                "Arche dials were designed with the purpose of measuring the present state of Arche’s waters, eventually revealing that the cyclical currents occurred on strict timing intervals.\\1" +
                "The number of gleaming blue slots describes the amount of time left before the next current cycle begins. When it approaches zero, I should start looking for shelter in the form of air pockets.");
        add(BLACK_MIRROR.get().getDescriptionId() + ".crafting", "By definition, a mirror’s reflection ought to be sharp and faultless, an immaculate representation of my worldly body.\\1" +
                "Yet, this one is not: the shape I see in its black stone is faint and blurred. Distorted. Inhuman.\\1" +
                "That is my goal: unconstrained by my physical form, the mirror becomes the little stronghold of my mind, and the blemishes on my reflection lead my focus away from the world my body touches and feels... back to the one my soul thinks and dreams. The only one I know is real.");
        add(BLACK_MIRROR.get().getDescriptionId() + ".crafting2", "The mirror is evil. Do not trust it. Do not trust what it says. The truths it shall utter will be incomplete. Do not trust it.");
        add(BLACK_MIRROR.get().getDescriptionId() + ".crafting3", "By definition, a mirror’s reflection ought to be sharp and faultless, an immaculate representation of our worldly body.\\1" +
                "Yet, this one is not: the shape we see in its black stone is faint and blurred. Distorted. Inhuman.\\1" +
                "That is our goal: unconstrained by our physical form, the mirror acts as the little stronghold of our mind, and all the blemishes on our reflection lead us away from the world our body touches and feels... back to the one our soul thinks and dreams. The only one we know is real.");
        add(DREAM_BOTTLE.get().getDescriptionId() + ".crafting", "A Shoreman device; in the past, they filled it with tears. When {§nopened§r}[caption:shift_right_click], up to four memory phials can be inserted. {§nDrinking§r}[caption:right_click] from it will then consume all the contained phials to dream of up to four memories at once.\\1" +
                "However, it can only be used once per day.");
        add(DREAM_BOTTLE.get().getDescriptionId() + ".crafting2", "A Shoreman device; in the past, they filled it with tears. When {§nopened§r}[caption:shift_right_click], up to four memory phials can be inserted. {§nDrinking§r}[caption:right_click] from it will then consume all the contained phials to dream of up to four memories at once.\\1" +
                "Normally, it can only be used once per day. However, filling it with tears overcomes this flaw, allowing me to use it as many times as I want as long as there is sufficient liquid.");
        add(EMERALD_GEM.get().getDescriptionId() + ".crafting", "A small jewel. Used in surgical insertion procedures.");
        add(FLASK_LARGE.get().getDescriptionId() + ".crafting", "Used for storing liquids or items for the purpose of surgical procedures. More information is in the surgeon’s journal.");
        add(JOURNAL.get().getDescriptionId() + ".crafting", "The repository of all my knowledge pertaining to surgical operations. This includes an overview of the procedures, required infrastructure and ingredients, as well as handwritten notes of my own experiments");
        add(LETTER_BOX.get().getDescriptionId() + ".crafting", "Used to send and receive correspondence – but I never manage to catch the mailman!");
        add(NAUTILUS.get().getDescriptionId() + ".crafting", "A portable submarine used by the old expeditioners. It can be {§ndeployed§r}[caption:right_click] easily, though its large size and bulky movement make it best suited for large bodies of water like the depths of Arche, and pretty much useless for tight ones such as rivers.");
        add(REPAIR_HAMMER.get().getDescriptionId() + ".crafting", "Used to repair the Nautilus §ofrom outside§r. {§nStriking§r}[caption:left_click] the submarine with it will slowly restore its frame.");
        add(FORCEPS.get().getDescriptionId() + ".crafting", "A tool for surgical insertions. More information is in the surgeon’s journal.");
        add(SCALPEL.get().getDescriptionId() + ".crafting", "A tool for surgical incisions. More information is in the surgeon’s journal.");
        add(SEWING_NEEDLE.get().getDescriptionId() + ".crafting", "A tool for surgical sewing. More information is in the surgeon’s journal.");
        add(SURGERY_BED.get().getDescriptionId() + ".crafting", "A wooden bed to hold patients awaiting operations on the chest or back. More information is in the surgeon’s journal.");
        add(SYRINGE.get().getDescriptionId() + ".crafting", "A tool for surgical injections. More information is in the surgeon’s journal.");
        add(TONGS.get().getDescriptionId() + ".crafting", "A tool for surgical extractions. More information is in the surgeon’s journal.");
        add(WATERY_CRADLE.get().getDescriptionId() + ".crafting", "A device to hold patients awaiting operations on the skull. More information is in the surgeon’s journal.");
        add(CANOE.get().getDescriptionId() + ".crafting", "A Shoreman design. Not dissimilar in speed and manoeuvrability from the boats I’m used to build, yet its distinct look acts almost as a coat of arms: an insignia designating the rider as a friend of the Shore – for better or worse, depending on the observer.");
        add(DAMP_WOOD.get().getDescriptionId() + ".crafting", "The wood in the Shoremen’s hamlet is soggy and brittle; there is scarcely a reason to use it, other than to imitate their design.\\1" +
                "The wood itself can be bought from the Shoreman carpenters, or (according to them) by using their water grasping technique to place water on a normal wooden log.");
        add(GEAR_BENCH.get().getDescriptionId() + ".crafting", "An advanced crafting station.");
        add(GEAR.get().getDescriptionId() + ".crafting", "An ordinary component in several mechanical crafts.");
        add(SAMPLE_TUBE.get().getDescriptionId() + ".crafting", "Used to collect fluids dropped by creatures on death: simply having one in the inventory when slaying specific enemies will have a chance to fill it up.\\1" +
                "The tube may then be emptied in a liquid container. Its contents may also be {§ndiscarded§r}[caption:shift_right_click_air].");
    }

    private void addReminiscence() {
        add("reminiscence.EMPTY", "The dream was not affected.");
        add("reminiscence.void", "My next dream will be empowered by void.");
        add("reminiscence.repair", "%1$s of my items were repaired.");
        add("reminiscence.blinded", "The target was blinded.");
        add("reminiscence.ice", "The target was encased in ice.");
        add("reminiscence.waypoint_other_dimension", "The destination lies in another dimension.");
        add("reminiscence.power.0", "%1$s of my effects became stronger.");
        add("reminiscence.power.1", "%1$s of the target's effects became stronger.");
        add("reminiscence.power.2", "Of the creatures near the target area, %1$s had their effects become stronger.");
        add("reminiscence.stillness.0", "%1$s of my effects became longer.");
        add("reminiscence.stillness.1", "%1$s of the target's effects became longer.");
        add("reminiscence.stillness.2", "Of the creatures near the target area, %1$s had their effects become longer.");
        add("reminiscence.change.0", "%1$s of my effects changed.");
        add("reminiscence.change.1", "%1$s of the target's effects changed.");
        add("reminiscence.change.2", "Of the creatures near the target area, %1$s had their effects changed.");
    }

    private void addGuis() {
        add("gui.sleep_chamber", "Sleep Chamber");
        add("gui.sleep_chamber.wake", "Leave chamber");
        add("gui.killed_by_cultist.respawn", "Respawn, right here, §oright now.");
        add("gui.dream_bottle.title", "Dream Bottle");
        add("gui.blood_gem.title", "Blood Gem");
        add("gui.surgery_bed.title", "Surgery Bed");
        add("gui.dagon.title", "Communion");

        addJournal();
        addLetterBox();
        addBaptism();
        addOverlays();
        addBloodPool();
        addBloodThesis();
        addDagon();


        add("gui.dialogue.bartender.display_name", "Bartender");
        add("gui.dialogue.carpenter.display_name", "Carpenter");
        add("gui.dialogue.clerk.display_name", "Clerk");
        add("gui.dialogue.drunk.display_name", "Drunk");
        add("gui.dialogue.fisherman.display_name", "Fisherman");
        add("gui.dialogue.lighthouse_keeper.display_name", "Lighthouse Keeper");
        add("gui.dialogue.miner.display_name", "Miner");
        add("gui.dialogue.scholar.display_name", "Scholar");
        add("gui.dialogue.smith.display_name", "Smith");

        add("gui.multiblock.layer", "Layer %d");

        add("gui.surgery_bed.leave", "Leave operation");
        add("gui.surgery_bed.leave_die", "Leave operation (die)");
        add("gui.surgery_bed.leave_complete", "Leave (operation completed)");

        add("container.deep_chest", "Deep Chest");
    }

    private void addDagon() {
        add("gui.dagon.0", "Hearken me, o fleeting children!");
        add("gui.dagon.1", "Ye are born of mine own blood, that ichor traversing all concreteness.");
        add("gui.dagon.2", "And now ye dive, to speak to the heart!");
        add("gui.dagon.3", "But do not squander what little time ye have.");
        add("gui.dagon.4", "Ye will die, ultimately, like all of thy kin.");
        add("gui.dagon.5", "Humanity shall forget thee,");
        add("gui.dagon.6", "and I shall forget humanity.");
        add("gui.dagon.7", "Now depart, and see it manifest.");
        add("gui.dagon.8", "Slay he who keeps the lighthouse, and embrace that snippet of Truth.");
    }

    private void addBloodThesis() {
        add("research.thesis.title.institute1", "Institute of");
        add("research.thesis.title.institute2", "Anthropological Studies");
        add("research.thesis.title.title1", "Sanguis");
        add("research.thesis.title.title2", "Inter Vitam et Mortem");
        add("research.thesis.title.thesis", "Master Thesis");
        add("research.thesis.title.author", "Georg Ferdinand Mauer");
        add("research.thesis.title.supervisor", "Supervisor: Henry Armitage");

        add("research.thesis.abstract.title", "Abstract");
        add("research.thesis.abstract", "Blood, as is known, makes for powerful bindings. \"Brothers in blood\", \"Pact of blood\", and so forth: one must not look far into popular customs among the many folks of this world to find such examples. The phenomenon has a scientific basis: blood binds all life, all things that live. A network of flesh, a web of souls, and its fulcrum is the deep sea Arche from which all things are born.\\n" +
                "Within this treatise, we shall focus on what stands perhaps as the most important of the bindings that blood can forge: that between life and death. We explore the fluid's role in this endeavor from a historical perspective, study its potential in breaching this veil, and conjecture possibilities for further advancements in the art.");

        add("research.thesis.chapter", "Chapter %1$s");

        add("research.thesis.background.title", "Introduction & Background");
        add("research.thesis.background.section.1", "1.1 Cultural significance");
        add("research.thesis.background.text.1", "It comes as no surprise that blood has, throughout history, been closely associated with relationships—that is, the bonds tying living beings together, the most basic of which being familial bloodlines. Various customs have sprung from this association, and bloodspill has been celebrated as a means to invigorate kinships or mend enmities.\\n" +
                "Furthermore, the red fluid’s symbolism extends to capture the essence of life itself; no other spectacle so accurately evokes such a phenomenon as does the sound of a beating heart, pumping sustenance to all other organs.\\n" +
                "Yet, it is undeniable that blood has also been historically associated with death; bloodshed remains a grim sight, reminding onlookers of their own mortality, and the resulting fetor drives one to sickness. Though bloodspill can be interpreted as a means of forging bonds among the living, so too can it be viewed as the gateway from life into what comes beyond.\\n" +
                "This insight lies at the heart of this thesis: the most powerful of the links born of blood is that across life and death themselves, and its effects in natural and human histories are clear to see. It is no wonder, for example, that undead creatures are so drawn to the beating hearts of those who live.\\n" +
                "Various peoples of the world have resorted to handling this attribute of blood to manipulate the connections born of it—from the smallest veins tying our limbs together, to the greatest ones tying all life to blood’s ancient precursor: water, and the deep ocean Arché from which all life is born. To do so, these peoples engage in blood sacrifices, materializing the link across life and death for a single human and, in so doing, achieve various effects.");
        add("research.thesis.background.section.2", "1.2 This work");
        add("research.thesis.background.text.2", "Throughout this thesis, we collect a number of notes via both archaeological studies and, where applicable, interviews with living members of these peoples, detailing the steps involved in the sacrificial process and some of the most common rituals. Most prominent among the researched cultures are the Desertfolk, as well as the elusive Blood Cult—the latter not being a people per se, but a collective of individuals with shared beliefs.\\n" +
                "Chapter 2 shall delineate the steps involved in performing rituals, as well as the necessary infrastructure. Chapter 3 will outline the reported risks in the sacrificial process, and the dangers stemming from the misuse of blood. Chapter 4 will briefly introduce a small variety of items used to partially alter the ritual process to various ends.\\n" +
                "Subsequently, we introduce a list of known rituals from the surveyed cultures—our purpose is purely academic and made for the purpose of documentation, and we have not engaged in any of the ceremonies, nor enacted them ourselves. Chapter 5 will describe creation rituals, used in manufacturing unusual items; Chapter 6 is a report of various binding rituals, aimed at creating links across objects and entities; finally, Chapter 7 explains manifestation rituals, used in bringing life to various effects or creatures.\\n" +
                "This work raises no ethical concerns.");

        add("research.thesis.ritual.title", "Performing the Ritual");
        add("research.thesis.ritual.preamble", "Despite the diversity of their practitioners across the world, all ritual sacrifice traditions employ the same components and maneuvers, suggesting a common origin. In this chapter, we describe the steps undertaken to enact such practice by the slayer, who may have been a priest, shaman, necromancer, or any other sort of miracle worker.");
        add("research.thesis.ritual.section.1", "2.1 Components");
        add("research.thesis.ritual.text.1", "The core of the ritual is the sacrifice altar: this has traditionally been used to embrace the victim, acting as both origin and destination of the blood nucleus. Living humans were placed upon the altar in order to fuel the procedure with their own lifeblood.\\n" +
                "The second major component is the blood basin: these apparatuses were placed around the altar to host the ingredients used in the ritual. Their position relative to the altar was deemed inconsequential, as long as they were not exceedingly distant.\\n" +
                "Finally, in order to start the ritual proper, the slayer would employ a sacrificial knife: a small curved blade, capable of inflicting precise cuts in the flesh.");
        add("research.thesis.ritual.section.2", "2.2 Starting the ritual");
        add("research.thesis.ritual.text.2", "Before the ritual began, two types of resources were required:\\n" +
                "• First, the offerings described by the ritual procedure. These were placed upon the basins surrounding the altar.\\1" +
                "• Then, the sacrifice: a mortal human that was placed upon the altar.\\n" +
                "Once all of that was done, the practitioner cut open the victim using the sacrificial knife. This did not start the ritual yet; rather, it bound slayer and martyr, allowing the former to extend the link over the necessary blood basins.\\n" +
                "Thus, the slayer would use the bloody knife—without ever letting it go from his or her hands—atop of each blood basin. This bound each offering contained in the basins, and had to be done in order, as prescribed by the ritual. According to reports, this would birth a veritable chain of blood, linking every ingredient.\\n" +
                "Finally, after binding every ingredient in order, the slayer used the knife on the victim once more, thus closing the loop. This began the ritual.");
        add("research.thesis.ritual.section.3", "2.3 Monitoring the process");
        add("research.thesis.ritual.text.3", "After the ritual’s start, a *blood nucleus would appear*, slowly traversing from victim to offering, to the next offering, and so forth, until returning to its origin. Upon reaching an offering, the nucleus would temporarily stop, burning the ingredient, and only proceeding after this was complete. Once the nucleus has returned to the victim, the ritual ends, successfully executing the intended effects.");

        add("research.thesis.risks.title", "Risks");
        add("research.thesis.risks.preamble", "Blood rituals were known to be perilous undertakings, with several unpredictable complications having been observed over time. There is no agreement as to the cause of this—some have proposed that the unwanted developments were due to the unnatural handling of blood, in itself an unstable substance; others claim that the blood nucleus held the victim’s remaining fragments of consciousness, and that stirring them awake would invoke retribution.\\n" +
                "Though we can only speculate as to what the true cause may have been, we have observed common patterns in the described events, consistent across different cultures. Below, we attempt to categorize these effects, along with the suggested response.");
        add("research.thesis.risks.section.1", "3.1 Rejecting offerings");
        add("research.thesis.risks.text.1", "The most common unexpected event was, quite simply, an ingredient being knocked out from the basin it was standing upon. Most of the time, the object was simply dropped on the ground; at times, however, it may have been utterly destroyed. For this reason, it was generally always recommended to hold copies of each offering ready at hand.\\n" +
                "Though the ritual could reject any offerings, so too would it lament any ones lost in this manner. This contradiction meant that if, at any point, the blood nucleus could not make progress due to a missing ingredient on the current basin, the \"instability\" of the ritual would have grown, leading to more problematic effects. It was thus imperative to replace any missing offerings forthwith: a basin lacking its ingredient would normally have emitted smoke, leading to easy identification.");
        add("research.thesis.risks.section.2", "3.2 Further effects");
        add("research.thesis.risks.text.2", "Rituals facing high instability could quickly turn into nightmares for the practitioner: various harmful events have been reported, including explosions, lightning strikes, and even the appearance of undead creatures, captivated by the ritual for obvious reasons.\\n" +
                "The greatest risk, of course, was an abrupt end to the ritual, signifying a loss of the ingredients and a waste of a life.");

        add("research.thesis.modifier.title", "Modifiers");
        add("research.thesis.modifier.section.1", "4.1 Altering the ritual");
        add("research.thesis.modifier.text.1", "Despite the associated risks, some practitioners deviated from the rigid recipe prescribed by the ritual descriptions, revealing a small degree of flexibility within their setup. Some rituals could thus be slightly modified in order to change their effects or the process of the sacrifice itself.\\n" +
                "To do this, additional offerings were placed on the blood basins surrounding the altar. During the linking process, after the initial cut but before the proper start of the ritual, the modifier ingredients would have to be linked first among all offerings. Below, we list some of the more common such modifiers.");
        add("research.thesis.modifier.section.2", "4.2 Modifiers");
        add("research.thesis.modifier.text.2", "• Reducing instability: [...] could reduce the risk of adverse effects occurring, starting from the risk of offerings being rejected in the first place.\\n" +
                "• Returning ingredients: a diamond, used as an offering, was said to provide a chance of some other offering in the ritual not being fully burned by the blood nucleus, allowing it to be salvaged.\\n" +
                "• Targeting locations: some rituals allowed for their effects to take place at specific locations. To do this, either a pathway sigil or immortal sigil could be used, the latter specifying the target individual’s current location (if within the same dimension).");

        add("research.thesis.creation.title", "Creation Rituals");
        add("research.thesis.creation.text.1", "Creation rituals involved the construction of items infused with blood’s properties. Thus, objects created in such a manner were able to create and utilize bindings, or heal and harm their wielder, or grant life and bring death to creatures. \\n" +
                "We have collected several such rituals, making this our largest chapter.");
        add("research.thesis.ritual.blood_bricks.title", "Blood Bricks");
        add("research.thesis.ritual.blood_bricks.text", "The victims’ blood could infuse normal stone bricks with its essence, leading credence that the resulting blood bricks contained some snippet of life—some claimed that walking within a structure built from these blocks could raise the echo of their deceased fathers and mothers, though this has never been corroborated with evidence.\\n" +
                "Most importantly, these were used in the construction of blood wells: structures said to open portal veins through the roots of the world for undead to walk through.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• Up to four stacks of stone bricks, of any size.");
        add("research.thesis.ritual.coral_staff.title", "Coral Staff");
        add("research.thesis.ritual.coral_staff.text", "Crafted from the sea invertebrates so resemblant of our own blood vessels, coral staffs epitomize the link between the red fluid and its ancient progenitor: water.\\n" +
                "These objects were indeed used to build links and, in so doing, command the undead. By using them on two hearts lying on the ground, one could bind them together, making any undead attracted to the first heart eventually proceed towards the second. The second heart could also be linked to a third one, and so forth, creating an arbitrarily long chain of bindings and leading to a veritable procession of the damned.\\n" +
                "Additionally, striking any creature with a coral staff had the effect of attracting the attention of any nearby undead towards the unfortunate victim.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A fire coral.\\1" +
                "• A Heart of the Sea.\\1" +
                "• A heart.");
        add("research.thesis.ritual.bleeding_belt.title", "Bleeding Belt");
        add("research.thesis.ritual.bleeding_belt.text", "A belt to be worn around the waist, adorned with small thorns. Whenever harm befell the wearer, the belt would prick the skin of the stomach, drawing blood.\\n" +
                "In so doing, the item would protect its owner from any damage, for as long as their stomach was not empty. Excessive use within a short timeframe could weaken the belt, but never irreversibly, as it would quickly repair itself on its own.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• Chains\\1" +
                "• Two great hearts\\1" +
                "• A cactus");
        add("research.thesis.ritual.living_iron.title", "Living Iron");
        add("research.thesis.ritual.living_iron.text", "Our findings reveal several attempts throughout history at granting life to objects via blood rituals. These tended to be crude experiments driven by trial and error, which predated more refined approaches such as the thaumaturgical art of golemancy.\\n" +
                "Here we describe the creation of living iron—one of the few successful attempts at animating inanimate objects. The resulting item was a simple, parasitical lifeform. It could not grow or feed on its own, and thus needed to attach itself to the spine of a living creature; according to our research, human beings were often used as the host, and their spine would be covered by iron until they could no longer walk.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• Up to three stacks of raw iron, of any size. One living iron will be created for every four chunks of raw material.");
        add("research.thesis.ritual.vessel_stone.title", "Vessel Stone");
        add("research.thesis.ritual.vessel_stone.text", "Vessel stones have become widely known in recent years due to their usage by Arché prospectors and hunters: their serendipitous discovery within the blood corals of the deepest ocean proved to be a way out of Arché for what would have otherwise been a suicide mission for the brave hunters. However, unbeknownst to them, vessel stones had already been discovered by ancient peoples, and even been replicated via blood rituals.\\n" +
                "We describe here a ritual used in the creation of such an object. Vessel stones allow their user to return to the surface when wielded in Arché.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• A block of emerald.\\1" +
                "• An empty bladder.\\1" +
                "• A chorus fruit.");
        add("research.thesis.ritual.blood_orb.title", "Blood Orb");
        add("research.thesis.ritual.blood_orb.text", "Blood orbs were used in conjunction with blood wells or bound IV stands as sorts of scrying stones: they would enable the user to view the undead creatures or convalescent patients that were bound to their blood pools, and even summon them back to the surface.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• A heart of the sea.\\1" +
                "• An egg.");
        add("research.thesis.ritual.blood_gem.title", "Blood Gem");
        add("research.thesis.ritual.blood_gem.text", "Blood gems were similar to blood orbs, in that they allowed people to interact with the creatures bound to the user’s blood pool. However, they were not used to view the bound entities; instead, they could only summon them, but were able to do so for multiple entities at a time.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• A diamond.\\1" +
                "• A nether quartz.");
        add("research.thesis.ritual.vein_miner.title", "Vein Miner");
        add("research.thesis.ritual.vein_miner.text", "A belt to be worn around the waist, adorned with small thorns. Whenever harm befell the wearer, the belt would prick the skin of the stomach, drawing blood.\\n" +
                "In so doing, the item would protect its owner from any damage, for as long as their stomach was not empty. Excessive use within a short timeframe could weaken the belt, but never irreversibly, as it would quickly repair itself on its own.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• A diamond pickaxe.\\1" +
                "• An empty bladder.");

        add("research.thesis.binding.title", "Binding Rituals");
        add("research.thesis.binding.text.1", "Binding rituals were used to create connections between objects or creatures. Oftentimes, this involved enhancing an item to act as either the nexus or an endpoint of a connection; for example, binding an object to its owner, or creating an object to bind creatures to its owner. \\n" +
                "We observe a great variety of shapes that these connections can take; so far, we have gathered four such rituals. In future work, we plan to extend this chapter, including new rituals such as those among immortal humans.");
        add("research.thesis.ritual.bind_item_damage.title", "Bind item (damage)");
        add("research.thesis.ritual.bind_item_damage.text", "This type of ritual was used by witches to gain power upon others, working as a sort of \"hex\" attached to arbitrary objects. The item would be bound to the one who performed the ritual; any other person possessing the item would come to much more severe harm than normal whenever struck by the bound individual.\\n" +
                "Thus, the challenge lay in trickery: how to hand the cursed object to the detested foe, while raising nary a hint of suspicion?\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• The item to be bound.\\1" +
                "• A diamond sword.\\1" +
                "• A scalpel.");

        add("research.thesis.ritual.bind_item_weakness.title", "Bind item (weakness)");
        add("research.thesis.ritual.bind_item_weakness.text", "The effect of this ritual is identical to the previous one, but reversed: any individual possessing the item will have its blows against the bound person become much less effective.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• The item to be bound.\\1" +
                "• An anvil.\\1" +
                "• A dorsal spine.");

        add("research.thesis.ritual.bind_pillar.title", "Bind pillar");
        add("research.thesis.ritual.bind_pillar.text", "Offer and demand pillars are used by blood cultists to act as endpoints within their trade operations. These are known to focus on livestock, although, despite their claims to the contrary during our interviews, there is evidence indicating that this may include human lives.\\n" +
                "Nonetheless, this ritual—also developed by the cultists—reveals an additional property of the pillars: rather than indicating a drop point for a trader, the demand pillar corresponding to the modified offer one will spawn an arbitrary creature. The particular entity is determined by the blood shard used in the ritual: such shards are obtained by repeatedly killing a certain type of creature using a sacrificial knife.\\n" +
                "Another difference with unmodified pillars is that life exchanges performed in this manner are far from being equal trades: it may take several newborn lives as offers before a single entity is spawned around the demand pillar.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• An offer pillar.\\1" +
                "• A blood shard specifying the creature that shall spawn near the corresponding demand pillar.");

        add("research.thesis.ritual.pool_flebo.title", "Bind IV stand");
        add("research.thesis.ritual.pool_flebo.text", "IV stands can supply patients with blood and nutrients during surgical operations, easing their pain. Yet, if the reports detailing this ritual speak true, they can be used to permanently bind the patient to the stand’s bound individual—that is, the one who performed the ritual.\\n" +
                "This technique would rely on similar principles as the blood well does for undead: any patient fed with fluid from such a stand would vanish into thin air, and be transported to some unknown location or realm—likely far down into the depths of the world. The only one capable of recovering such patients and bringing them back to our world would be the stand’s bound human, via the use of a blood gem.\\n" +
                "Besides the stand, the ritual requires three other items, which will dictate the location that patients will be assigned to in the form of a color code. Any dye may be used, as well as clay balls (the latter signifying \"no dye\"). This is done for the purpose of organizing the patients, in accordance with the blood gem’s wielder’s needs.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• Three dyes or clay balls.\\1" +
                "• The IV stand.");

        add("research.thesis.ritual.binding_overworld.title", "Binding of the Overworld");
        add("research.thesis.ritual.binding_overworld.text", "The Binding of the Overworld is the first of the three known world bindings. These rituals were used to attune the slayers to a particular dimension’s energy, thereby bestowing upon them superhuman abilities–most of which required a Blood Fist to be performed, and whose energy would have to be replenished after prolonged use.\\n" +
                "This particular binding attuned the user to the earthly, living world. The bound individual was able to more easily place large amounts of materials on the ground at once, as well as destroy objects in front or below by simply walking into them. Additionally, they could repair their clothes and gear by lending any life force in excess.\\n" +
                "Finally, they would partake in any bestowal of life, finding healing in any nearby births of creatures.");

        add("research.thesis.ritual.binding_nether.title", "Binding of the Nether");
        add("research.thesis.ritual.binding_nether.text", "The Binding of the Nether is the second known world binding. Where the Overworld is attuned to life, the Nether is to death. Individuals with this binding would find healing in nearby deaths of creatures, and satiation in flames–which they were also known to spread. They were also able to swiftly dispatch weak or weakened creatures, albeit at a cost in health.\\n" +
                "Finally, the binding was also associated with control, and the mindless creatures serving bound users (such as blood undead) would benefit from increased strength.\n");

        add("research.thesis.ritual.binding_end.title", "Binding of the End");
        add("research.thesis.ritual.binding_end.text", "The Binding of the End is the third and last known world binding. As is known (and well studied by institutions such as the Miskatonic), this dimension has different spatiotemporal properties from most others, and its residents are capable of manipulating this idiosyncrasy; this is reflected in the binding.\\n" +
                "Individuals attuned to the End were capable of teleporting large vertical distances with ease, as well as confuse melee opponents by quickly changing position. They were also known to be able to occasionally avoid death via a massive spatiotemporal leap, often being found alive in another state or province instead of falling dead where they stood.\\n" +
                "Additionally, they were able to divert attention from themselves onto other creatures when under attack. Finally, it was common among practitioners bound to the End to utilize Elytras (a wing design developed by an unknown End civilization), which they could reportedly fly more easily after undergoing the binding.");


        add("research.thesis.manifestation.title", "Manifestation Rituals");
        add("research.thesis.manifestation.text.1", "Manifestation rituals leveraged blood’s ability to grant life to creatures, after bringing death to the ritual victim. However, only a few such rituals are known to have existed; in this work, we have gathered two.");
        add("research.thesis.ritual.summon_wither.title", "Summon Wither");
        add("research.thesis.ritual.summon_wither.text", "Known to be terribly dangerous—their summoning has been forbidden within the walls of the Miskatonic, as well as the city of Arkham as a whole—Withers remain nonetheless a common goal among hunters, both for the challenge and the precious star animating them from their core.\\n" +
                "This ritual was used to ease the summoning of withers, by supplementing a single wither skull with a deceased victim’s blood. However, its high instability may have made it more trouble than it was worth, and no instances of the ritual have been documented in several decades.\\n" +
                "According to reports, the ritual could also target specific locations via appropriate modifiers.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• Soul sand.\\1" +
                "• A single wither skeleton skull.");

        add("research.thesis.ritual.summon_living_portal.title", "Summon Living Portal");
        add("research.thesis.ritual.summon_living_portal.text", "The Nether is known to lie roughly two thirds of the way from the surface to Arché; it has thus been proposed that it may affect blood’s properties, altering its link with water. We believe this to likely play a role in the blood nucleus’s burning of the ritual offerings.\\n" +
                "More importantly, blood’s attunement with the Nether can lead to a link being forged between it and our world: this ritual was used to create a portal and bring life to it. The resulting gateway could not be broken, and would have to be killed; for as long as it lived, it would also bring forth creatures from the Nether into the world.\\n" +
                "We believe that this ritual could also target specific locations via appropriate modifiers.\\n" +
                "\\n" +
                "Ingredients:\\n" +
                "• A heart.\\1" +
                "• A nether star.\\1" +
                "• A blaze rod.\\1" +
                "• A ghast tear.");
    }

    private void addBloodPool() {
        add("gui.blood_pool.health", "Health: ");
        add("gui.blood_pool.spawn", "Spawn");
    }

    private void addOverlays() {
        add("overlay.repair_hammer.submarine", "Integrity: %.2f%%");
    }

    private void addBaptism() {
        add("gui.drowned.option_0_0", "Summon what strength is left and leave this hole.");
        add("gui.drowned.option_0_1", "Let go.");
        add("gui.drowned.option_1_0", "The world is gone. There is nothing outside this hole.");
        add("gui.drowned.option_1_1", "Pray to The Great Dreamer.");
        add("gui.drowned.option_1_2", "Let go.");
        add("gui.drowned.option_2_0", "The world is gone. There is nothing outside this hole.");
        add("gui.drowned.option_2_1", "The Great Dreamer is not listening.");
        add("gui.drowned.option_2_2", "Then who is listening?");
        add("gui.drowned.option_2_3", "It is so easy to let go.");
        add("gui.drowned.option_3_0", "Talk to the Gnawing Sensation.");
        add("gui.drowned.option_3_1", "Talk to the Ocean.");
        add("gui.drowned.option_3_2", "Talk to yourself.");
        add("gui.drowned.option_3_3", "Just let go.");
        add("gui.drowned.option_4_0", "Talk to the Gnawing Sensation.");
        add("gui.drowned.option_4_1", "Talk to the Ocean.");
        add("gui.drowned.option_4_2", "Talk to yourself.");
        add("gui.drowned.option_4_3", "Just let go.");

        add("gui.drowned.gnawing", "I am the gnawing sensation at the back of your mind.");
        add("gui.drowned.gnawing2", "You are not immortal.");
        add("gui.drowned.insignificant", "Insignificant");
        add("gui.drowned.worthless", "Worthless");
        add("gui.drowned.ocean", "I am the Ocean, now still and tranquil.");
        add("gui.drowned.ocean2", "Let us talk, just the two of us.");
        add("gui.drowned.you", "I am You.....");
        add("gui.drowned.you2", "but no, no no...");
        add("gui.drowned.you3", "You weren't supposed to know yet!");
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
        add("gui.journal.overview.abominations", "Abominations");
        add("gui.journal.overview.what_is", "What is an abomination?");
        add("gui.journal.overview.capacity", "Capacity");
        add("gui.journal.overview.modifiers", "Modifiers");
        add("gui.journal.overview.triggering", "Triggering & Targeting");
        add("gui.journal.overview.priorities", "Targeting Priorities");
        add("gui.journal.overview.weepers", "Weepers");
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
        add("gui.journal.overview.fluids.text", "Liquid ingredients, such as {§nsedative§r}[link:journal.ingredients.sedative], {§ncoagulant§r}[link:journal.ingredients.coagulant], and several others, can be injected into patients to achieve various results. This requires a {§nsyringe§r}[link:journal.tools.syringe], and does not require an incision on the patient.\\n" +
                "Such fluids may be stored in liquid flasks ({§nsmall§r}[link:journal.tools.flasks], {§nmedium§r}[link:journal.tools.flasks] and {§nlarge§r}[link:journal.tools.flasks]), and are often produced with the help of {§nalembics§r}[link:journal.tools.alembics].");
        add("gui.journal.overview.solids.text", "Solid ingredients may be inserted in the patient. This requires {§nforceps§r}[link:journal.tools.forceps], and can only be done when the incision is open.\\n" +
                "Such ingredients are generally stored in appropriate containers, such as {§nitem jars§r}[link:journal.tools.item_flask].");
        add("gui.journal.overview.extraction.text", "Extractions can be performed either on the back or the chest, and are used to obtain respectively the patient’s spine or heart. They always require an incision to be already present, and will likely cause severe bleeding after the operation. They are generally very painful.\\n" +
                "Extractions are performed with {§ntongs§r}[link:journal.tools.tongs]. Interrupting one resets progress.");
        add("gui.journal.overview.incision.text", "Incisions can be performed either on the back or the chest, and are used to open up a wound that may then allow insertions or extractions. A patient cannot be moved (neither removed from the surgery bed nor flipped) while incised. They are generally very painful.\\n" +
                "Incisions are performed with the {§nscalpel§r}[link:journal.tools.scalpel]. Interrupting one resets progress.");
        add("gui.journal.overview.injection.text", "Injections can be performed on any surgical location: back, chest and skull. They are carried out with a {§nsyringe§r}[link:journal.tools.syringe] after it has been filled with fluids. Interrupting an injection does not reset progress, as the fluid already resides within the patient’s body.\\n" +
                "Injections may be painful depending on the fluid type and quantity.");
        add("gui.journal.overview.insertion.text", "Insertions can be performed either on the back or the chest, and always require an incision to be present. They are performed with {§nforceps§r}[link:journal.tools.forceps], after they have been used to grab a solid ingredient. Interrupting an insertion resets progress.");
        add("gui.journal.overview.stitching.text", "Stitching is used to sew the incision made through a scalpel. It is performed with {§nthread and needle§r}[link:journal.tools.sewing_needle]. Stitching is painless and instantaneous.");
        add("gui.journal.overview.what_is.text", "It is known that certain ingredients – both liquid and solid – are capable of radically altering the shape of a human when entering the body. Abominations are the product of injecting or inserting these ingredients in the back of humans with an intact spine. \\n" +
                "These creatures are not so dissimilar from creepers, in that their primary mode of offense is via detonation of their own body. Unlike creepers, however, the resulting blast is not damaging by itself; rather, it is the abomination’s blood that, striking either friend or foe, may result in a variety of effects.\\n" +
                "Thus, an abomination blood can cover the enemy in webbing, or afflict them with poison, or force spasms in their hands until they drop their weapons; likewise, they may also heal their master, or camouflage me, or make my visage fearsome to my foes.\\n" +
                "All of this is dictated by the ingredients that are used in operations. Any ingredient that requires capacity can be used to create an abomination; this includes both solids (added via insertions) and liquids (via injections). One must first make sure that there is sufficient space (i.e. capacity) in the patient’s back to accommodate all these ingredients.\\n" +
                "Used correctly, abominations can prove themselves a powerful weapon: a true living arsenal, with the possibility of mixing and matching different abominations to swarm the enemy. They synergise especially well with blood gems, allowing me to summon multiple at a time from different categories.");
        add("gui.journal.overview.capacity.text", "Capacity determines how many (and which) ingredients can be used when creating an abomination, and thus which effects and modifiers can be applied.\\n" +
                "Some ingredients are capable of increasing the available capacity, and are thus essential to create abominations. It is likely that only very few such ingredients exist, limiting the maximum possible capacity on a given individual.\\n" +
                "When adding an ingredient that requires capacity, it must be made sure that there is enough to satisfy the need; if the available capacity ever drops below zero, the patient will die.");
        add("gui.journal.overview.modifiers.text", "There are two types of modifiers: potency and burst. The former increase the strength of the individual effects applied to the abomination; the latter increase the size of the burst.\\n" +
                "Multiple potency modifiers can be added at any point of the operation, and can generally be applied multiple times. The first instance of a certain potency modifier will increase the strength of the first effect ingredient that has been or that will be applied to the soon-to-be abomination; the second instance will increase the strength of the second effect ingredient; and so forth.\\n" +
                "For example, assume we are creating an abomination with a \"create webbing\" effect and an \"apply poison\" effect, added in that order. If we want to improve the potency of the webbing twice and of the poison once, we can add potency modifier \"liquid glowstone\" twice and potency modifier \"diamond powder\" once, at any point during the operation (assuming sufficient capacity). The first application of the liquid glowstone and diamond powder will improve the webbing’s potency; the second application of the liquid glowstone will improve the poison’s potency.\\n" +
                "Burst modifiers are only applied once, and are independent of the effect ingredients that are used.");
        add("gui.journal.overview.triggering.text", "An abomination can be made to act on the occurrence of certain events, and move towards certain targets; the former are known as triggering instructions, and are dictated by injection of serum GS121; the latter are targeting instructions, dictated by serum SA245. Both of them can be one of five options, determined by the amount x of the respective serum injected:\\n" +
                "- Hostile creatures nearby (1 ≤ x < 5 mb).\\1" +
                "- Immortal humans (other than the abomination’s master) nearby (5 ≤ x < 10 mb).\\1" +
                "- Abomination’s master nearby (10 ≤ x < 15 mb).\\1" +
                "- Entity attacking the abomination (15 ≤ x < 20 mb).\\1" +
                "- Entity attacking the abomination’s master (20 ≤ x < 25 mb).\\1" +
                "- Entity attacked by the abomination’s master (25 ≤ x < 30 mb).\\n" +
                "If no serum SA245 is injected, the default targeting instruction is \"hostile creatures nearby\". If no serum GS121 is injected, the default triggering will be the same as the targeting instruction.\\n" +
                "The ability to specify triggering and targeting separately gives me more control on how the abominations must act. \\n" +
                "For example, if there are three abominations around me, each with \"Entity attacked by the abomination’s master\" triggering instruction and \"Hostile creatures nearby\" targeting instruction, I can simply hit any creatures (including one of the abominations themselves, if I want) to tell each of them to attack the closest hostile creature.\\n" +
                "An abomination’s burst will only affect an ally (i.e. its master and its master’s other minions) if the target of the explosion was an ally; it will only affect creatures other than allies if the target of the explosion was not an ally.");
        add("gui.journal.overview.priorities.text", "An abomination can be assigned to a certain \"mutual exclusion (mutex) category\" defined by a single dye color; this is done via cheap liquid dyes. Two abominations assigned to the same mutex will not target the same creature at the same time, though they may target two different creatures, or the same creature at different times (e.g. after one abomination has exploded).\\n" +
                "This can be useful when I wish to apply a certain effect before another one, for example vulnerability before harming. In this case, I can give two different abominations the \"lime\" mutex; then, I would make sure that the vulnerability abomination attacks first. I could do this by summoning the vulnerability abomination first, or somehow triggering it first; however, the simplest way is simply to give the harming abomination lower priority, which will make sure it attacks afterwards. Deciding priorities is done via injection of serum PP456; the higher the amount, the lower the priority (no injection means highest).");
        add("gui.journal.overview.weepers.text", "Weepers have an intrinsic capacity of 15, and may thus be used as abominations. However, their capacity cannot be increased further.");

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

        add("gui.journal.tools.sample_tube.title", "Sample Tube");
        add("gui.journal.tools.sample_tube.text", "Used to collect fluids dropped by creatures on death: simply having one in the inventory when slaying specific enemies will have a chance to fill it up.\\n" +
                "The tube may then be emptied in a liquid container. Its contents may also be {§ndiscarded§r}[caption:shift_right_click_air].");

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
        add("gui.journal.ingredients.living_iron.text", "An animated chunk of metal, yet not strong enough to survive on its own. It must be attached to the spine of an individual, and then left to grow on the host as they roam unaware.\\n" +
                "Eventually the patient will drop on the ground, unable to move anymore. That will be the right time to extract its spine, hopefully acquiring a greater amount of iron than was used in the creation of the living metal.");

        add("gui.journal.ingredients.empty_bladder.title", "Empty Bladder");
        add("gui.journal.ingredients.empty_bladder.text", "A small sac, capable of holding other ingredients within itself. Occasionally dropped by monsters when slain with a scalpel.");

        add("gui.journal.ingredients.parental_hormones.title", "Parental Hormones");
        add("gui.journal.ingredients.parental_hormones.text", "Can be injected into the skull. A spineless patient affected by such hormones may be placed on a chest or other container. It will then try to breed any nearby eligible pair of animals using items from the container, if available (e.g. wheat for cows, seeds for chickens).");

        add("gui.journal.ingredients.obedience_hormones.title", "Obedience Hormones");
        add("gui.journal.ingredients.obedience_hormones.text", "Can be injected into the skull. A spineless patient affected by such hormones may be placed on a chest or other container. It will then pick up any items within a certain range and put them in the container it was assigned to.");

        add("gui.journal.ingredients.vasoconstrictor.title", "Vasoconstrictor");
        add("gui.journal.ingredients.vasoconstrictor.text", "Results in a tightening of the blood vessels within the host creature, and thus an increase in space for other ingredients.");

        add("gui.journal.ingredients.tears.title", "Tears");
        add("gui.journal.ingredients.tears.text", "As the mind weeps away, so does it begin to detest the body; the host creature will no longer deny extraneous agents from entering it.");

        add("gui.journal.ingredients.liquid_glowstone.title", "Liquid Glowstone");
        add("gui.journal.ingredients.liquid_glowstone.text", "Not unlike its powdery counterpart, this substance can increase the potency of abomination ingredients. Inserting it as a liquid rather than solid does wonders in reducing the pain inflicted on the patient.");

        add("gui.journal.ingredients.diamond_powder.title", "Diamond Powder");
        add("gui.journal.ingredients.diamond_powder.text", "Diamonds grounded into not-so-fine dust. Very painful for the patient.");

        add("gui.journal.ingredients.liquid_gold.title", "Liquid Gold");
        add("gui.journal.ingredients.liquid_gold.text", "Terribly heavy – and blazing hot to the touch. Quite painful to inject, but does not require much capacity.");

        add("gui.journal.ingredients.organochloride.title", "Organochloride");
        add("gui.journal.ingredients.organochloride.text", "A strong pesticide, detested by all arthropods. Sometimes found in Arche.");

        add("gui.journal.ingredients.pheromones.title", "Pheromones");
        add("gui.journal.ingredients.pheromones.text", "Producing a strong yet amiable smell, it immediately alerts nearby creatures; however, attraction may quickly sour, as it turns to envy and then hatred: the victim will soon be targeted by several foes.");

        add("gui.journal.ingredients.wart_serums.title", "Wart Serums");
        add("gui.journal.ingredients.wart_serums.text", "These liquids are produced via combining classical potion ingredients with base serum A00B3. They mimic the effects of normal potions as abomination ingredients.\\n" +
                "Interestingly, these may also be injected in the chest rather than back. In doing so, they will not turn patients into explosive abominations, but rather apply those effects to the patients themselves (for an extended period of time compared to normal potions). They will still require capacity, and may also still be affected by potency modifiers, as normal.");

        add("gui.journal.ingredients.emerald_gem.title", "Emerald Gem");
        add("gui.journal.ingredients.emerald_gem.text", "A small trinket, used to create jewelry from within patients’ bodies.");

        add("gui.journal.ingredients.swollen_growth.title", "Swollen Growth");
        add("gui.journal.ingredients.swollen_growth.text", "A large abnormal excrescence. We have much less control over our bodies than we believe.\n" +
                "Dropped by some creatures in Arche.");

        add("gui.journal.ingredients.periosteum_growth.title", "Periosteum Growth");
        add("gui.journal.ingredients.periosteum_growth.text", "A bony protuberance. There's a certain unease at seeing your true self beneath that curtain of flesh.");

        add("gui.journal.ingredients.chromatosphore_gland.title", "Chromatosphore Gland");
        add("gui.journal.ingredients.chromatosphore_gland.text", "Used for mimicry. The need to veil your true self is present even in nature.");

        add("gui.journal.ingredients.osteoclast_gland.title", "Osteoclast Gland");
        add("gui.journal.ingredients.osteoclast_gland.text", "Destroys bony formations. Very harmful against undead, from skellies to withers.");

        add("gui.journal.ingredients.slime_heart.title", "Slime Heart");
        add("gui.journal.ingredients.slime_heart.text", "Not truly a \"heart\", but a sort of reproductive organ: it is through this that dying slimes will divide and conquer.");

        add("gui.journal.journal.type.none", "Select Type");
        add("gui.journal.journal.type.position", "Position");
        add("gui.journal.journal.type.extraction", "Extraction");
        add("gui.journal.journal.type.incision", "Incision");
        add("gui.journal.journal.type.injection", "Injection");
        add("gui.journal.journal.type.insertion", "Insertion");
        add("gui.journal.journal.type.stitching", "Stitching");
        add("gui.journal.journal.type.complication", "Complication");
        add("gui.journal.journal.type.death", "Death");
        add("gui.journal.journal.completeness.complete", "Completed");
        add("gui.journal.journal.completeness.incomplete", "Not completed");
        add("gui.journal.journal.location.none", "None");
        add("gui.journal.journal.location.back", "Back");
        add("gui.journal.journal.location.chest", "Chest");
        add("gui.journal.journal.location.skull", "Skull");
        add("gui.journal.journal.complication.pain", "Pain");
        add("gui.journal.journal.complication.hardness", "Hardness");
        add("gui.journal.journal.complication.bleeding", "Bleeding");
        add("gui.journal.journal.ingredient.none", "Select Ingredient");
        add("gui.journal.journal.fluid.none", "Select Fluid");
        add("gui.journal.journal.new", "New Report");
        add("gui.journal.journal.edit", "Edit");
        add("gui.journal.journal.save", "Save");
        add("gui.journal.journal.delete", "Delete");
        add("gui.journal.journal.cancel", "Cancel");
        add("gui.journal.journal.print", "Print");
        add("gui.journal.journal.need_paper", "Need paper in inventory!");
        add("gui.journal.journal.type.success", "§2Success");
        add("gui.journal.journal.type.partial_success", "§ePartial Success");
        add("gui.journal.journal.type.failed", "§4Failure");
        add("gui.journal.journal.type.in_progress", "In Progress");
        add("gui.journal.journal.type.human", "Human");
        add("gui.journal.journal.type.weeper", "Weeper");
    }

    private void addSurgery() {
        add("surgery.status.status", "Status: ");
        add("surgery.status.capacity", "Capacity: ");
        add("surgery.status.arsenal", "Burst Effect: ");
        add("surgery.status.arsenal_amplifier", "Amplifier: ");
        add("surgery.status.arsenal_duration", "Extender: ");
        add("surgery.status.burst_extension", "Burst Extender: ");
        add("surgery.status.mutex", "Mutex: ");
        add("surgery.status.trigger_type", "Trigger: ");
        add("surgery.status.target_type", "Target: ");
    }

    private void addArsenalAndEffects() {
        add("arsenal.harm_undead", "Harm Undead");
        add("arsenal.harm_arthropods", "Harm Arthropods");
        add("arsenal.enweb", "Enweb");
        add("arsenal.damage_armor", "Damage Armor");
        add("arsenal.knock_upwards", "Knock Upwards");
        add("arsenal.create_slime", "Create Slime");
        add("arsenal.everyone_target", "Attract Attackers");

        add(BTVEffects.FOLLY.get(), "Folly");
    }

}
