import java.util.*;
import java.util.stream.Collectors;

public class InDevLabs {
    public static void main(String[] args) {
        testWinners();
        System.out.println();
        testDecrypting();
    }

    public static void testWinners() {
        //test1
        String[] test1 = new String[3];
        test1[0] = "dancer1; 10 10 10"; //1
        test1[1] = "dancer2: 10 9 10";//2
        test1[2] = "dancer3- 10 8 10";//3

        //test2
        String[] test2 = new String[3];
        test2[0] = "dancer1 9 10 9"; //2
        test2[1] = "dancer2 9 10 9";//2
        test2[2] = "dancer3 9 20 9";//1

        //test3
        String[] test3 = new String[3];
        test3[0] = "dancer1 10 10 10 9"; //1
        test3[1] = "dancer2 6 6";//3
        test3[2] = "dancer3 9 9 9";//2

        //test4
        String[] test4 = new String[3];
        test4[0] = "dancer1 10 10 10"; //1


        String result1 = winners(test1);
        String result2 = winners(test2);
        String result3 = winners(test3);
        String result4 = winners(test4);

        System.out.println("Test winners 1: " + "The 1st place:dancer1 ;The 2nd place:dancer2 ;The 3rd place:dancer3 ;".equals(result1));
        System.out.println("Test winners 2: " + "The 1st place:dancer3 ;The 2nd place:dancer2 dancer1 ;The 3rd place: ;".equals(result2));
        System.out.println("Test winners 3: " + "The 1st place:dancer1 ;The 2nd place:dancer3 ;The 3rd place:dancer2 ;".equals(result3));
        System.out.println("Test winners 4: " + "The 1st place:dancer1 ;The 2nd place: ;The 3rd place: ;".equals(result4));
    }

    public static void testDecrypting () {
        String test1 = "t1e1s2t, Hell1o, 1th1e, fir2st1";
        String test2 = "1yo1ur; ro2ckst2ar;i2nn1er. us6. Come unl1eash 2wi2th1";
        String test3 = "2par3ty 1o1ur1 y1ou 1in1 po4ol cinema Waiting";
        String test4 = "In2vita1tion 1Night1 Cine1ma Po1ol Mo1vie";
        System.out.println("test decrypting 1: " + decrypting(test1));
        System.out.println("test decrypting 2: " + decrypting(test2));
        System.out.println("test decrypting 3: " + decrypting(test3));
        System.out.println("test decrypting 4: " + decrypting(test4));
    }

    public static String decrypting(String code) {
        //task write own method
        //if any words has same priority, leave last word
        SortedMap<Integer, String> words =
                new TreeMap<>(Comparator.comparingInt(i -> i)); //collect words by priority
        //decoder
        for (String s : code.trim().split("\\W+")) {
            StringBuilder word = new StringBuilder();
            int priority = 0;
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    priority += Integer.parseInt(String.valueOf(c));
                } else {
                    word.append(c);
                }
            }
            words.put(priority, String.valueOf(word));
        }
        //make result string
        StringBuilder resultMessage = new StringBuilder();
        for (String s : words.values()) {
            resultMessage.append(s).append(" ");
        }
        resultMessage.deleteCharAt(resultMessage.length()-1);
        return resultMessage.toString();
    }

    public static String winners(String[] participantsList) {
        //task do refactor for it work right code (source code in down)
        Map<String, Double> averageScores = new HashMap<>();

        for (String s : participantsList) {
            if (s != null) {
                double sumOfScores = 0;
                int countOfScores = 0;
                StringBuilder participantName = new StringBuilder();

                for (String word : s.trim().split("\\W+")) {
                    try {
                        sumOfScores += Integer.parseInt(word);
                        countOfScores++;
                    } catch (NumberFormatException ex) {
                        participantName.append(word).append(" ");
                    }
                }
                Double avgScore = (sumOfScores / countOfScores);
                averageScores.put(participantName.toString(), avgScore);
            }
        }
        Map<String, Double> sortedScores =
                averageScores.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Double previousScore = 0.0; //
        StringBuilder sbWinners = new StringBuilder();
        String[] place = new String[]{"The 1st place:", ";The 2nd place:", ";The 3rd place:"};
        Iterator<Map.Entry<String, Double>> iterator = sortedScores.entrySet().iterator();
        int check = 0;

        while (iterator.hasNext()) {
            Map.Entry<String, Double> entry =  iterator.next(); //
            if (check != 0 && (double) entry.getValue() == previousScore) {
                sbWinners.append(entry.getKey()); // тут заходит когда оценки равны
            } else {

                sbWinners.append(place[check]); //тут
                sbWinners.append(entry.getKey());
                previousScore =  entry.getValue();
                check++;
            }
        }

        for (int i = check; i < 3; i++) {
            sbWinners.append(place[i]).append(" ");
        }
        sbWinners.append(";");

        return sbWinners.toString();

        //source code
//        public static String winners( String[] participantsList ) {
//            double [] average = new double[participantsList.length];
//
//            java.util.Map<String, Integer> scores = new java.util.LinkedHashMap<String, Integer>();
//
//            for(int i = 0; i< participantsList.length; i++) {
//                String participant = participantsList[i];
//                String[] words = participant.trim().split("\\W+");
//                int sumOfScores = 0;
//                int numberOfScores = 0;
//                StringBuffer participantName = new StringBuffer();
//                for(String word: words) {
//                    try{
//                        sumOfScores += Integer.parseInt(word);
//                    } catch (NumberFormatException ex) {
//                        participantName.append(word).append(" ");
//                    }
//                }
//                scores.put(participantName.toString(),sumOfScores);
//            }
//
//            java.util.Map<String, Integer> sortedScores = scores.entrySet().stream()
//                    .sorted(java.util.Map.Entry.<String, Integer>comparingByValue())
//                    .collect(java.util.stream.Collectors.toMap (java.util.Map.Entry::getKey, java.util.Map.Entry::getValue, (x, y) -> y, java.util.LinkedHashMap::new));
//
//
//            java.util.Iterator it = sortedScores.entrySet().iterator();
//            int i = 0;
//            String[] winners = new String[3];
//
//            while(it.hasNext()&&(i < 3)) {
//                java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
//                winners[i] =  entry.getKey().toString();
//                int previousScore = (Integer) entry.getValue();
//                i++;
//            }
//
//            StringBuilder sbWinners = new StringBuilder();
//            for(int i = 0; i<3; i++) {
//                if(!winners[i]==null)
//                    sbWinners.append("The ").append(i+1).
//                            append()
//            }
//            sbWinners.append("The 1st place:").append(winners[0]).
//                    append(";The 2nd place:").append(winners[1]).
//                    append(";The 3rd place:").append(winners[2]).
//                    append(";");
//
//            return sbWinners.toString();
//        }
    }


}

