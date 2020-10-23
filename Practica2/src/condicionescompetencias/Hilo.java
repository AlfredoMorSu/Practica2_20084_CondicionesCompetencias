package condicionescompetencias;

/**
 *
 * @author José Alfredo Moreno Suárez
 */
import javax.swing.JTextArea;

public class Hilo extends Thread{
    private JTextArea area;
    private RCompartido rc;
    private boolean pausa = false;
    private boolean corriendo = true;
    
    public Hilo(JTextArea area, RCompartido rc){
        this.area=area;
        this.rc=rc;
    }
    
    @Override
    public void run(){
        try{
            while(true){
                rc.setDatoCompartido(this.getName());
                area.append(rc.getDatoCompartido()+"\n");
                sleep(1500);
                synchronized(this){
                    if(pausa)
                        wait();
                    if(!corriendo)
                        join();
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void pausa(){
        this.pausa = true;
    }
    
    public void reanudar(){
        synchronized(this){
            pausa = false;
            notifyAll();
        }
    }
    
    public void detener(){
        this.corriendo = false;
    }
}
