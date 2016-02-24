/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 */
public class Doorman extends Thread{
	/**
	 * Creates a new doorman.
	 * @param queue		The customer queue.
	 * @param gui		A reference to the GUI interface.
	 */
	private CustomerQueue customerQueue;
    private Gui gui;
    private boolean finished = false;

	public Doorman(CustomerQueue queue, Gui gui) { 
		this.customerQueue = queue;
        this.gui = gui;
	}

	/**
	 * Starts the doorman running as a separate thread.
	 */
	public void startThread() {
		this.start();
	}

	/**
	 * Stops the doorman thread.
	 */
	public void stopThread() {
		this.finished = true;
	}

    /**
     * Runs the doorman thread.
     */
    public void run() {
        while (!finished) {
            try {
                /** Doorman is sleeping times a random amount, before next action. */
                this.gui.println("Doorman is sleeping");
                Thread.sleep (Globals.doormanSleep);
            }
            catch (InterruptedException interruptedException) {
                System.out.println( "Thread is interrupted when it is sleeping" + interruptedException);
            }

            /** New customer arrives from street. */
            this.customerQueue.addCustomer(new Customer());
            this.gui.println("New customer has arrived");
        }
    }
}
