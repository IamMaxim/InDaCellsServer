package ru.iammaxim.InDaCellsServer.World;

import ru.iammaxim.InDaCellsServer.Creatures.Mobs.*;
import ru.iammaxim.InDaCellsServer.Creatures.NPCs.*;
import ru.iammaxim.InDaCellsServer.Quests.Quest;

public class WorldCreator {
    public static void createMobs(World world) {
        //Town
        world.getCell(0, 1).addCreature(new Dog(world));
        world.getCell(-1, 0).addCreature(new Hedgehog(world));
        world.getCell(-1, 1).addCreature(new Beggar(world)).addCreature(new Beggar(world));
        world.getCell(1, -1).addCreature(new Beggar(world));
        world.getCell(2, 0).addCreature(new Dog(world)).addCreature(new Dog(world)).addCreature(new Dog(world));
        world.getCell(0, 2).addCreature(new Trasher(world)).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(-2, 0).addCreature(new Trasher(world));

        //Tod's house
        world.getCell(-2, -7).addCreature(new Raider(world)).addCreature(new Raider(world)).addCreature(new Raider(world));

        //Raider's base.
        world.getCell(-1, -5).addCreature(new Raider(world));
        world.getCell(-1, -4).addCreature(new Raider(world));
        world.getCell(0, -5).addCreature(new Raider(world)).addCreature(new Raider(world));

        //Ruins
        world.getCell(1, 3).addCreature(new Hedgehog(world)).addCreature(new Hedgehog(world));
        world.getCell(2, 1).addCreature(new Dog(world));
        world.getCell(2, 4).addCreature(new Fox(world));
        world.getCell(2, 5).addCreature(new Hedgehog(world)).addCreature(new Hedgehog(world)).addCreature(new Hedgehog(world));

        //Mountains
        world.getCell(-18, 18).addCreature(new Beggar(world));
        world.getCell(16, -18).addCreature(new Beggar(world));
        world.getCell(13, -19).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(-13, -18).addCreature(new Beggar(world));

        //Top Trasher's way
        world.getCell(16, 19).addCreature(new Beggar(world));
        world.getCell(16, 15).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(16, 14).addCreature(new Trasher(world));
        world.getCell(16, 9).addCreature(new Trasher(world));
        world.getCell(16, 5).addCreature(new Dog(world)).addCreature(new Dog(world));
        world.getCell(16, 1).addCreature(new Dog(world));

        //Bottom Trasher's way
        world.getCell(16, -2).addCreature(new Dog(world));
        world.getCell(16, -4).addCreature(new Dog(world)).addCreature(new Dog(world));
        world.getCell(16, -7).addCreature(new Trasher(world));
        world.getCell(16, -10).addCreature(new Trasher(world));
        world.getCell(16, -16).addCreature(new Trasher(world)).addCreature(new Trasher(world));
        world.getCell(16, -18).addCreature(new Beggar(world));
    }

    public static void createDescriptions(World world) {
        world.getCell(0, 0).setDescription("В центре города стоит трехэтажная ратуша окруженная одно-двух этажными домиками и полукольцом рынка. Иногда на площади перед ратушей можно увидеть стайку пробегающих детей, чей негромкий смех эхом дробится в дальних улочках. Обшарпанные и кривоватые стены. засыпанные котлованы и газоны не давали забыть о прошедшей катострофе даже в самой отстроенной части города.");
        world.getCell(0, 1).setDescription("Заброшенные строения до которых еще не дошла реставрация смотрели на мир пустыми глазницами выбитых окон и развалившихся стен. Одна из улиц давно не видела солнечного света перекрытая бетонными блоками бывших этажей. Жители стараются не заглядывать сюда после темноты. Судя по душераздирающим звукам здесь обосновался дикий пес. Возможно животное где-то застряло и спокойте боги душу того, что его высвободит.");
        world.getCell(1, 0).setDescription("Заброшенные строения до которых еще не дошла реставрация. Битое стекло прорезает даже крепкую обувь, обломки штукатурки осыпаются на головы от малейшего ветерка. Между домами блуждает неутихающее эхо.");
        world.getCell(0, -1).setDescription("Заброшенные строения до которых еще не дошла реставрация, уцелевшие стекла разбрасывают на стены солнечных зайчиков. Уцелевшие первые этажи могут хранить в своих недах водные массы, сносящие своих освободителей прочь.");
        world.getCell(-1, 0).setDescription("Заброшенные строения до которых еще не дошла реставрация. В тишине можно услышать громкий топот ежика, и горе вам если вы на него наступите.");
        world.getCell(1, 1).setDescription("Окраина города приспособленная под свалку с руинами домов. Голые остовы зданий хищьно устремлены вверх грозя неудачно упавшему нелегкую смерть от кровопотери. Тучи мух и бломков металлолома не привлекают городское население для посещения этих мест.");
        world.getCell(-1, 1).setDescription("Окраина города приспособленная под свалку с руинами домов. Тучи мух и бломков металлолома не привлекают городское население для посещения этих мест, но оказалось весьма привлекательно для двух агрессивных и голодных бомжей. Узнать о их приближении можно по усилившемуся запаху отходов и жужжанию мух.");
        world.getCell(1, -1).setDescription("Заброшенные руины на окраине города. Тихое,относительно чистое место облюбованное больным бомжом. Опознается по жалобным стонам и пролятиям всем окружающим.");
        world.getCell(-1, -1).setDescription("В юго-западной части города руины были как умеренно загаженные обломками после наводнения, так и умеренно заросшие лианами и мхом. в полуразрушенном доме с растущим на крыше неопознанным деревом жил торговец-мусорщик. Он был достаточно нелюдим и не любил когда рядом с его домом было много людей. Мечтает приручить пса и посадить его на цепь.");
        world.getCell(2, 0).setDescription("На восточной части города гордо возвышаясь над растущими рогами металлолм сояла ЛЭП. Остатки будки с торчащими проводами искрили и грозились перемкнуть напряжение во время дождя, вопреки заботливо постеленному поверх брезенту.");
        world.getCell(0, 2).setDescription("Северная окраина была облюбована 3 рейдерами взимающими мзду с проходящих через их территорию, что не мешает елать им переодические набеги на центр. Лабиринты свалок и руин изученные ими как свои пять пальцев не позволяют быстро и без потерь их уничтожить.");
        world.getCell(-2, 0).setDescription("Западная окраина города не могла похвастаться особыми достопримечательностями. Разве что полубезумный мусорщик построивший свою берлогу из кусков шифера и травящий байки о старом мире в пустоту ночи. Говорят что он был нормальным, пока случайно не упал на свалочного ежа... ");
        world.getCell(0, -2).setDescription("На южной окраине устота и забршенность. Эти развалины никому не нужны.");
    }

    public static void createNPCs(World world) {
        //Town
        world.getCell(0, 0)
                .addCreature(new RoyFirstMarshal(world).attachQuest(Quest.quests.get(2)))
                .addCreature(new JoCaptain(world).attachQuest(Quest.quests.get(3)));

        //Tod's house
        world.getCell(-2, -7).addCreature(new TodOldFarmer(world));

        //Raider's base.
        world.getCell(-1, -5)
                .addCreature(new GeorgRaidersLeader(world).attachQuest(Quest.quests.get(0)))
                .addCreature(new RammCrusher(world).attachQuest(Quest.quests.get(1)));
    }
}
