import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;
import com.virtualpairprogrammers.services.BuildATutorWizard;


/*
 * I'm testing Extended Entity Managers with a simple test harness - of course you 
 * can turn this in to a Web Application if needed, but the principle is the same
 */
public class TestOfExtendedEntityManagers 
{
	public static void main(String[] args) throws Exception
	{
		// get the stateful EJB.
		
		Context jndi = new InitialContext();
		BuildATutorWizard wizard = (BuildATutorWizard)jndi.lookup("java:global/mywebapp/BuildATutorWizardImpl");
		
		// step 1.
		Tutor tutor = wizard.createNewTutor("Alan Jones", 109000);
		
		// now get all students
		List<Student> studentsWithoutATutor = wizard.getAllStudentsWithNoTutor();
		
		// let's just add them all!
		for (Student next : studentsWithoutATutor)
		{
			// we need to send ids because these are remoted objects
			wizard.addStudentToTutorGroup(next.getId(), tutor.getId());
		}
		
		// we are now finished with this wizard. Close the entity manager.		
		wizard.completeProcess();
	}
}
