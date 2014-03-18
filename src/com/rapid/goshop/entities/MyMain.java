package com.rapid.goshop.entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public class MyMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("GoShop");
		
		EntityManager em = emf.createEntityManager();
		
		/*UserInfo user = new UserInfo();
		user.setEmail("something@email.com");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        
        em.persist(user);*/
		
		/*TypedQuery<Process> createQuery = em.createQuery("select p from Process p",Process.class);
		
		List<Process> resultList = createQuery.getResultList();
		
		for(Process process : resultList){
			System.out.println(process.getProcessName());
			List<Skill> skillList = process.getSkillList();
			for(Skill skill: skillList){
				System.out.println(skill.getSkillAbb()+" -- "+skill.getSkill_type().getConfigName());
			}
		}*/
		
		/*Configuration config = em.find(Configuration.class,1);
		//createQuery.setParameter(1,	"Skill");
		
		List<Configuration> children = config.getChildren();
		
		for(Configuration child: children){
			System.out.println(child.getConfigId()+" --> "+child.getConfigName());
		}
		*/
		
		/*TypedQuery<Student> studentQuery = em.createQuery("select s from Student s",Student.class);
		
		List<Student> rsList = studentQuery.getResultList();
		
		for(Student st : rsList){
			System.out.println("***"+st.getName()+"***");
			List<Address> addressList = st.getAddress();
			for(Address address : addressList){
				System.out.println(address.getCity());
			}
		}
		*/
		
		
		
		
		
		em.getTransaction().begin();
		/*
		//Configuration table		
		// 1. config table data
		Configuration config_parent1 = new Configuration();
		Configuration config_parent2 = new Configuration();
		Configuration config_parent3 = new Configuration();
		
		Configuration config_parent1_child1 = new Configuration();
		Configuration config_parent1_child2 = new Configuration();
		Configuration config_parent2_child1 = new Configuration();
		Configuration config_parent2_child2 = new Configuration();
		Configuration config_parent3_child1 = new Configuration();
		Configuration config_parent3_child2 = new Configuration();
		
		config_parent1.setConfigName("location");
		config_parent2.setConfigName("businessDriver");
		config_parent3.setConfigName("processType");
		
		config_parent1_child1.setConfigName("India");
		config_parent1_child2.setConfigName("Usa");
		
		config_parent2_child1.setConfigName("Call Volume");
		config_parent2_child2.setConfigName("AHT");

		config_parent3_child1.setConfigName("Planning");
		config_parent3_child2.setConfigName("Production");
		
		config_parent1.getChildren().add(config_parent1_child1);
		config_parent1.getChildren().add(config_parent1_child2);
		
		config_parent2.getChildren().add(config_parent2_child1);
		config_parent2.getChildren().add(config_parent2_child2);
		
		config_parent1_child1.setParent(config_parent1);
		config_parent1_child2.setParent(config_parent1);
		
		config_parent2_child1.setParent(config_parent2);
		config_parent2_child2.setParent(config_parent2);

		config_parent3_child1.setParent(config_parent3);
		config_parent3_child2.setParent(config_parent3);
		
		
		em.persist(config_parent1);
		em.persist(config_parent2);
		em.persist(config_parent3);
		em.persist(config_parent1_child1);
		em.persist(config_parent1_child2);
		em.persist(config_parent2_child1);
		em.persist(config_parent2_child2);
		em.persist(config_parent3_child1);
		em.persist(config_parent3_child2);
		
		
		// 2. client data entered to system
		//Client Table
		Client client = new Client();
		client.setClientName("Client_1");
		client.setClientAddress("USA");
		client.setCreatedBy("Admin1");
		em.persist(client);
		*/
		
		//3. process information goes to system
		/*Configuration process_type = em.find(Configuration.class, 8);
		Client client = em.find(Client.class ,  1);
		
		Process process = new Process();
		process.setProcessName("Process1");
		process.setProcess_type(process_type);
		process.setClient(client);
		process.setStatus(true);
		em.persist(process);
		*/
		//4. skill and skill business driver entered to db
		/*Skill skill = new Skill();
		skill.setSkillName("AOS cellular");
		skill.setSkillAbb("AOS");
		
		Configuration skill_type= em.find(Configuration.class, 4);
		skill.setSkill_type(skill_type);
		
		Process process = em.find(Process.class, 1);
		skill.setProcess(process);
		
		TypedQuery<Configuration> configL = em.createQuery("select c from Configuration c where c.parent.configId=2",Configuration.class);
		List<Configuration> config = configL.getResultList();
		skill.setConfigList(config);
		
		em.persist(skill); */
		
		//5. break position rule table is filled
		/*BreakPositionRule breakPositionRule = new BreakPositionRule();
		breakPositionRule.setRuleDescription("Rule1");
		breakPositionRule.setFirstRestBreak((float) 2.2);
		breakPositionRule.setFirstMealBreak((float) 3.4);
		breakPositionRule.setLastRestBreak(4);
		breakPositionRule.setLastMealBreak((float) 5.7);
		breakPositionRule.setTimeBetweenRestAndMealBreaks((float)1.2);
		breakPositionRule.setTimeBetweenTwoMealBreaks((float)1.4);
		breakPositionRule.setTimeBetweenTwoRestBreaks( 1.8f);
		
		em.persist(breakPositionRule); */
		
		//6. break count rule
		/*BreakCountRule breakCountRule = new BreakCountRule();
		breakCountRule.setDescription("Break1");
		breakCountRule.setRestBreakLength(2.8f);
		em.persist(breakCountRule);
		*/
		
		/*Shift shift = new Shift();
		shift.setShiftDesc("Shift1");
		
		shift.setProcess(em.find(Process.class, 1));
		shift.setBreakCountRule(em.find(BreakCountRule.class,1));
		shift.setBreakPositionRule(em.find(BreakPositionRule.class,1));
		shift.setStatus(true);
		em.persist(shift);*/
		
		/*
		//Process Table
		Process process = new Process();
		process.setProcessName("Process_1");
		process.setProcess_type(config_child2);
		process.setClient(client);
		client.getProcessList().add(process);
		em.persist(process);
		
		//Skill Table
		Skill skill = new Skill();
		skill.setSkillName("Skill_1");
		skill.setSkillAbb("SK1");
		skill.setSkill_type(config_child2);
		process.getSkillList().add(skill);
		skill.setProcess(process);
		em.persist(skill);
		*/
		
		/*
		Student student = new Student();
	    student.setId(20);
	    student.setName("tom_1");
	    Address addr1 = new Address();
	    Address addr2 = new Address();
	    
	    addr1.setStreet("street_3");
	    addr1.setCity("city_3");
	    addr1.setState("state_3");
	    addr1.setZip("zip_3");
	    
	    addr2.setStreet("street_4");
	    addr2.setCity("city_4");
	    addr2.setState("state_4");
	    addr2.setZip("zip_4");
	    
	    student.getAddress().add(addr1);
	    student.getAddress().add(addr2);
	    
	    em.persist(student);		
		
		em.getTransaction().commit(); */
		em.getTransaction().commit();
		em.close();
		emf.close();
		

	}

}
