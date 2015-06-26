package de.greenrobot.dao;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Schema schema = new Schema(2, "de.greenrobot.dao");
		schema.setDefaultJavaPackageTest("de.greenrobot.daoexample.test");
		schema.setDefaultJavaPackageDao("de.greenrobot.daoexample.dao");
		
		schema.enableKeepSectionsByDefault();
		
		addNote(schema);
		
		try {
			new DaoGenerator().generateAll(schema, "../ttttt/badmintonManager/badmintonManager_v0.1/src");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void addNote(Schema schema) {
        Entity match = schema.addEntity("Match");
        
        match.setTableName("MatchTable");
        
        match.addIdProperty().primaryKey().autoincrement();
        match.addDateProperty("time");
        match.addStringProperty("area");
        match.addStringProperty("manager");
        match.addStringProperty("menbers");
        match.addStringProperty("paidMenbers");
        match.addIntProperty("price");
        match.addBooleanProperty("isComplete");
        
        Entity member = schema.addEntity("Member");
        
        member.setTableName("MemberTable");
        
        member.addIdProperty().primaryKey().autoincrement();
        member.addStringProperty("name");
        member.addIntProperty("balance");
        member.addIntProperty("grandTotal");
	}
}
