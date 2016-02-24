/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 */
public class Barber extends Thread {
	/**
	 * Creates a new barber.
	 * @param queue		The customer queue.
	 * @param gui		The GUI.
	 * @param pos		The position of this barber's chair
	 */
	private CustomerQueue customerQueue;
    private Gui gui;
    private int position;
    private boolean finished = false;

    public Barber(CustomerQueue queue, Gui gui, int position) {
		this.customerQueue = queue;
        this.gui = gui;
        this.position = position;
	}

	/**
	 * Starts the barber running as a separate thread.
	 */
	public void startThread() {
		this.start();
	}

	/**
	 * Stops the barber thread.
	 */
	public void stopThread() {
		this.finished = true;
	}

    /**
     * Runs the barber thread.
     */
    public void run() {
        while (!finished) {
            try {
                /** Barber is sleeping times a random amount, before next action. */
                this.gui.barberIsSleeping(this.position);
                Thread.sleep (Globals.barberSleep);
                this.gui.barberIsAwake(this.position);
                this.gui.println("Barber #" + this.position + " is waiting for customers...");

            }
            catch (InterruptedException interruptedException) {
                System.out.println( "Thread is interrupted when it is sleeping" + interruptedException);
            }

            /** Next customer from customerQueue. */
            Customer nextCustomer = customerQueue.nextCustomer();
            this.gui.println("Barber #" + this.position + " start working on a new customer");
            this.gui.fillBarberChair(this.position, nextCustomer);
            try {
                /** sleep until barber is finished. */
                Thread.sleep(Globals.barberWork);
            }
            catch (InterruptedException interruptedException) {
                System.out.println( "Thread is interrupted when it is sleeping" + interruptedException);
            }

            /** Finished with customer. */
            this.gui.emptyBarberChair(this.position);
            this.gui.println("Barber #" + this.position + " is finished with a customer");
        }
    }
}

