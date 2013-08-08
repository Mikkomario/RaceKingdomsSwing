package handleds;


/**
 * Logicalhandleds are all handled in the logic thread and each of them can 
 * be activated and inactivated if needed.
 *
 * @author Gandalf.
 *         Created 8.12.2012.
 */
public interface LogicalHandled extends Handled
{
	/**
	 * @return Is the object currently interested in logival events
	 */
	public boolean isActive();
	
	/**
	 * Tries to restart the objects logical processes
	 * @return Were the processes started
	 */
	public boolean activate();
	
	/**
	 * Tries to stop the objects logical processes
	 * @return Were the processes stopped
	 */
	public boolean inactivate();
}
