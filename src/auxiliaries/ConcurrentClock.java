/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliaries;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.concurrent.Task;

/**
 *
 * @author qiyao
 */
public class ConcurrentClock extends Task<Void>{
    void getTime() {
        LocalTime localTime = LocalTime.now();
        updateMessage(localTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
    }

    protected Void call() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                if (isCancelled()) {
                    break;
                }
            }
            if (isCancelled()) {
                break;
            }
            getTime();
        }
        return null;
    }
}
