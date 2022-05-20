/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliaries;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Session;
import poiupv.PoiUPVApp;

/**
 *
 * @author qiyao
 */
public class sessionDataAux {
    public static final List<Session> userSessionHistory = PoiUPVApp.currentUser.getSessions();
    private static final LocalDate minDate = userSessionHistory.get(0).getLocalDate();
    private static final LocalDate maxDate = LocalDate.now();
    private static double totalGrade;
    private static int totalCorrect = 0;
    private static int totalIncorrect = 0;
    private static double totalCorrectPercentage;
    private static double totalIncorrectPerceentage;
    
    /**
     * Initializes the data auxiliary:
     * Initializes the history of sessions of the current user
     * Calculates the total correct answer through out the user history
     * Calculates the total incorrect answers through out the user history
     * Calculates the correct and incorrect percentages
     * Calculates the grade of the user
     */
    public static void initData() {
        if (!userSessionHistory.isEmpty()) {
            for (int aux = 0; aux < userSessionHistory.size(); aux++) {
                System.out.println("session" + aux + " Correct: " + userSessionHistory.get(aux).getHits() + "    Faults: " + userSessionHistory.get(aux).getFaults());
                totalCorrect += userSessionHistory.get(aux).getHits();
                totalIncorrect += userSessionHistory.get(aux).getFaults();
            }
            if (totalCorrect != 0 || totalIncorrect != 0) {
                System.out.println(totalCorrect + " " + totalIncorrect);
                totalGrade = calcGrade(totalCorrect, totalIncorrect);
                totalCorrectPercentage = calcCorrectPercentage(totalCorrect, totalIncorrect);
                totalIncorrectPerceentage  = calcIncorrectPercentage(totalCorrect, totalIncorrect);
            }
        }
    }  
    
    /**
     * List of sessions in a time frame
     * @param inputMinDate Date to filter from
     * @param inputMaxDate Date to filter to
     * @return list of sessions starting from inputMinDate until inputMaxDate
     */
    public static List<Session> getTimeframe(LocalDate inputMinDate, LocalDate inputMaxDate) {
        List<Session> result = new ArrayList<>();
        if (!userSessionHistory.isEmpty()) {
            //User selected a day before hes first session
            if (minDate.isAfter(inputMinDate)) {
                LocalDate auxDate;
                int counter = 0;
                do {
                    result.add(userSessionHistory.get(counter));
                    auxDate = userSessionHistory.get(counter).getLocalDate();
                    counter++;
                } while (auxDate.isBefore(inputMaxDate) || auxDate.isEqual(inputMaxDate));
                return result;
            }
        }
        return null;
    }
    
    /**
     * Calculates the total amount of correct answers given a sessionList
     * @param sessionList list of sessions
     * @return total correct answers of the list
     */
    public static int getCorrect(List<Session> sessionList) {
        int res = 0;
        for (int aux = 0; aux < sessionList.size(); aux++) {
            res += sessionList.get(aux).getHits();
        }
        return res;
    }
    
    /**
     * Calculates the total amount of incorrect answers given a sessionList
     * @param sessionList list of sessions
     * @return total incorrect answers of the list
     */
    public static int getIncorrect(List<Session> sessionList) {
        int res = 0;
        for (int aux =0; aux < sessionList.size(); aux++) {
            res += sessionList.get(aux).getFaults();
        }
        return res;
    }
    
    /**
     * calculates the grade of the user given the number of:
     * @param correct number of correct answers
     * @param incorrect number of incorrect answers
     * @return user grade
     */
    public static double calcGrade(int correct, int incorrect) {
        if (correct != 0) {
            return (double) correct / (correct + incorrect) * 10;
        }
        return 0;
    }
    
    /**
     * Grade getter (grade comprising all of the sessions)
     * @return grade of all of the users sessions
     */
    public static double getGrade() {
        return totalGrade;
    }
    
    /**
     * Calculates the percentage of correct answers using:
     * @param correct number of correct answers
     * @param incorrect number of incorrect answers
     * @return percentage of correct answers
     */
    public static double calcCorrectPercentage(int correct, int incorrect) {
        if (correct != 0) {
            return (correct * 100) / (correct + incorrect);
        }
        return 0;
    }
    
    /**
     * Calculates the percentage of incorrect answers using:
     * @param correct number of correct answers
     * @param incorrect number of incorrect answers
     * @return percentage of incorrect answers
     */
    public static double calcIncorrectPercentage(int correct, int incorrect) {
        if (incorrect != 0) {
            
            return (incorrect * 100) / (correct + incorrect);
        }
        return 0;
    }
    
    
    
    
    //Getters
    public static List<Session> getAllSessions() {
        return userSessionHistory;
    }
    
    public static int getTotalCorrect() {
        return totalCorrect;
    }
    
    public static int getTotalIncorrect() {
        return totalIncorrect;
    }
    
    public static double getTotalCorrectPercentage() {
        return totalCorrectPercentage;
    }
    
    public static double getTotalIncorrectPercentage() {
        return totalIncorrectPerceentage;
    }
    
    public static void midSessionUpdate() {
        calcGrade(totalCorrect, totalIncorrect);
        calcCorrectPercentage(totalCorrect, totalIncorrect);
        calcIncorrectPercentage(totalCorrect, totalIncorrect);
    }
    
    
    
    
    // Variable modifiers
    
    public static void increaseCorrect() {
        totalCorrect++;
    }
    
    public static void increaseIncorrect() {
        totalIncorrect++;
    }
    
}
