/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {
	/**
	 * Creates a new customer queue.
	 * @param queueLength	The maximum length of the queue.
	 * @param gui			A reference to the GUI interface.
	 */
	private Customer[] customerQueue;
    private Gui gui;
    private int nextIn, nextOut, count, queueLength;
    private boolean isFull() { return this.queueLength == this.count; }
    private boolean isEmpty() { return this.count == 0; }

    public CustomerQueue(int queueLength, Gui gui) {
		customerQueue = new Customer[queueLength];
        this.gui = gui;
        this.queueLength = queueLength;
        this.nextIn = 0;
        this.nextOut = 0;
        this.count = 0;
	}

    public synchronized void addCustomer(Customer newCustomer) {
        while (isFull()) {
            /** Wait until customerQueue isn't full anymore. */
            try {
                this.wait();
            }
            catch(InterruptedException interruptedException) {
                System.out.println( "Thread is interrupted when it is waiting" + interruptedException);
            }
        }

        /** Add customer to queue. */
        this.customerQueue[this.nextIn] = newCustomer;
        this.gui.fillLoungeChair(this.nextIn, newCustomer);
        this.nextIn = (this.nextIn + 1) % this.queueLength;
        this.count ++;

        /** Notify threads about one new customer. */
        notifyAll();
    }

    public synchronized Customer nextCustomer() {
        while (isEmpty()) {
            /** Wait until customerQueue isn't empty anymore. */
            try {
                this.wait();
            }
            catch(InterruptedException interruptedException) {
                System.out.println( "Thread is interrupted when it is waiting" + interruptedException);
            }
        }

        /** Send next customer to barber. */
        Customer customer = this.customerQueue[this.nextOut];
        this.gui.emptyLoungeChair(this.nextOut);
        this.nextOut = (this.nextOut + 1) % this.queueLength;
        this.count --;

        /** Notify threads about one less customer waiting. */
        notifyAll();

        return customer;
    }
}
