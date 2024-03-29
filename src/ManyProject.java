import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ManyProject {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Integer row = validateInputNumber("-> Config total rows in hall : ", "only number from 1-9", input);
        Integer col = validateInputNumber("-> Config total cols in hall : ", "only number from 1-9", input);
        String[][] morningHall = new String[row][col];
        String[][] afternoonHall = new String[row][col];
        String[][] eveningHall = new String[row][col];
        String[] bookingHostory = new  String[0];
        initlizeAllHall(morningHall, afternoonHall, eveningHall);
        char choce;
        do {
            mainMenu();
            choce = validateInputChar("-> Choose option : ", ">".repeat(50) + "\n# Option must be alphabet from A-F\n" + ">".repeat(50), "[a-fA-F]+", input);
            switch (choce) {
                case 'a' -> bookingHostory = booking(morningHall, afternoonHall, eveningHall, bookingHostory);
                case 'b' -> showAllHall(morningHall, afternoonHall, eveningHall);
                case 'c' -> showTimeMenu();
                case 'd' -> rebootAllHall(morningHall, afternoonHall, eveningHall,bookingHostory);
                case 'e' -> displayBookingHistory(bookingHostory);
                case 'f' -> {
                    System.out.println(" Goode bye See you again!!");
                    System.exit(0);
                }
                default -> System.out.println(">".repeat(50) + "\n# Please Input option from A-F\n" + ">".repeat(50));

            }
        } while (true);
    }

    // Booking Seat
    public static String[] booking(String[][] morningHall, String[][] afternoonHall, String[][] eveningHall, String[] currentHistory) {
        Scanner scanner = new Scanner(System.in);
        showTimeMenu();
        Character option = validateInputChar("Please select Show Time( A | B | C ):", "Please Input A-B-C", "[a-cA-C]", scanner);
        String[][] hall = getHall(morningHall, afternoonHall, eveningHall, option);
        displayOneHall(hall);
        String[] userInput = singleAndMultipleSelect();
        String seat = Arrays.toString(userInput);
        String[] newHistory = Arrays.copyOf(currentHistory, currentHistory.length + 1);
        boolean isTrue = true;
        boolean validateUserInput = true;
        char isSure = 0;
        String userName = "";
        Integer userID = 0;
        for (String input : userInput) {
            String getUserInput = input.replaceAll("-", "");
            int number = Integer.parseInt(getUserInput.replaceAll("[^0-9]", ""));
            char letter = getUserInput.replaceAll("[^a-zA-Z]", "").charAt(0);
            for (int i = 0; i < hall.length; i++) {
                for (int j = 0; j < hall[i].length; j++) {
                    char alphabet = (char) ('A' + i);
                    if (alphabet == letter && (number - 1) == j) {
                        if (hall[i][j].equals("BO")) {
                            System.out.println(">".repeat(50));
                            System.out.println("!![" + alphabet + "-" + (i + j) + "] Already booking.....!!");
                            System.out.println("!![" + alphabet + "-" + (i + j) + "] Cannot be booked because of unavailability.......!!");
                            System.out.println(">".repeat(50));
                            isTrue = false;
                            break;
                        } else {
                            if (validateUserInput) {
                                userID = validateInputNumber("->Please Inter  ID :","!! Your ID wrong !!! : ",scanner);
                                userName = validateInputString("-> Please Inter userName :", "!! Name cannot be special Character !!!: ", "[a-zA-Z\\s]+", scanner);
                                isSure = validateInputChar("Are you sure to book? (Y/N) :", "Please Input (Y or N)", "[yYNn]+", scanner);
                                validateUserInput = false;
                            }
                            if (isSure == 'y') {
                                hall[i][j] = "BO";
                                // Update Booking History  array
                                String history = addToHistory(seat, userName,option,userID);
                                newHistory[newHistory.length - 1] = history;
                            } else {
                                System.out.println(">".repeat(50));
                                System.out.println("> Cancel Booking.......??");
                                System.out.println("> Done Cancel ");
                                System.out.println(">".repeat(50));
                                isTrue = false;
                            }
                        }
                    }
                }
            }
        }
        if (isTrue) {
            System.out.println(">".repeat(50));
            System.out.println("#" + seat + " Booked Successfully........ ");
            System.out.println(">".repeat(50));
        }
        return newHistory;
    }

    // Method ValidateNumber
    public static Integer validateInputNumber(String message, String error, Scanner input) {
        while (true) {
            System.out.println(message);
            String option = input.nextLine();
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(option);
            if (matcher.matches()) {
                if (!(Integer.parseInt(option) == 0))
                    return Integer.parseInt(option);
                else
                    System.out.println("It Can't be ZERO");
            } else
                System.out.println(error);
        }
    }

    // Method Validate Char
    public static Character validateInputChar(String message, String error, String stringPattern, Scanner input) {
        while (true) {
            System.out.println(message);
            String option = input.nextLine();
            Pattern pattern = Pattern.compile(stringPattern);
            Matcher matcher = pattern.matcher(option);
            if (matcher.matches())
                return Character.toLowerCase(option.charAt(0));
            else
                System.out.println(error);
        }
    }
    // Method Validate String
    public static String validateInputString(String message, String error, String stringPattern, Scanner input) {
        while (true) {
            System.out.println(message);
            String option = input.nextLine();
            Pattern pattern = Pattern.compile(stringPattern);
            Matcher matcher = pattern.matcher(option);
            if (matcher.matches()) {
                return option;
            } else
                System.err.println(error);
        }
    }

    // Method InitializeAllHall
    public static void initlizeAllHall(String[][] morningHall, String[][] afternoonHall, String[][] eveningHall) {
        initHall(morningHall);
        initHall(afternoonHall);
        initHall(eveningHall);
    }

    //  Method Init Hall
    public static void initHall(String[][] hall) {
        for (String[] strings : hall) {
            Arrays.fill(strings, "AV");
        }
    }

    // Method OneGetHall
    public static String[][] getHall(String[][] morningHall, String[][] afternoonHall, String[][] eveningHall, Character option) {
        if (option.equals('a')) {
            return morningHall;
        } else if (option.equals('b')) {
            return afternoonHall;
        } else {
            return eveningHall;
        }
    }

    // Method  MainMenu
    public static void mainMenu() {
        System.out.print("""
                    [[.......... Application Menu ..........]]
                    <A> Booking
                    <B> Hall
                    <C> Showtime
                    <D> Reboot Showtime
                    <E> History
                    <F> Exit
                    """);
    }
    //  Method Show Time Menu
    public static void showTimeMenu(){
        System.out.print("""
                    ..........................................................
                    ..........................................................
                    # Show Time of Our Company  :
                    # A) Morning (8:30AM - 11:30PM)
                    # B) Afternoon (12:30PM - 3:30PM)
                    # C) Evening (5:00PM - 08:30PM)
                    ..........................................................
                    ..........................................................
                    """);
    }

    // Method  Display Booking History
    public static void displayBookingHistory(String[] bookingHistory) {
        boolean isFound = false;
        System.out.println(">".repeat(50));
        System.out.println("Booking History :");
        for (String m : bookingHistory) {
            if (!Objects.equals(m, "") && m != null) {
                System.out.println("-".repeat(50));
                System.out.println(m);
                System.out.println("-".repeat(50));
                System.out.println("-".repeat(50));
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("-".repeat(50));
            System.out.println(" There is no history ??");
            System.out.println("-".repeat(50));
            System.out.println("-".repeat(50));
        }
    }

    // Method addToHistory
    public static String addToHistory(String userName, String seat, Character option,Integer userID) {
        UUID uuid = UUID.randomUUID();
        String uniqueID = uuid.toString();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y hh:mm");
        String formattedDateTime = localDateTime.format(formatter);
        char hall = Character.toUpperCase(option);
        return String.format(
                "#SEATS: " + seat +
                        "\n#BookingID : " + uniqueID +
                        "\n#HALL         #USER.NAME               #CREATED AT" +
                        "\nHALL %-8s  %-23s #USER.ID:%-20s "
                , hall, userName.toUpperCase(),userID, formattedDateTime);
    }

    // Method RebootHall
    public static  void rebootAllHall(String[][] morningHall,String[][] afternoonHall,String[][] eveningHall,String[] histories){
        Scanner scanner = new Scanner(System.in);
        char isSure = validateInputChar("Are you sure to reboot hall... ??"," Please input (Y or N)","[yYnN]+", scanner);
        if(isSure== 'y'){
            initHall(morningHall);
            initHall(afternoonHall);
            initHall(eveningHall);
            clearHistory(histories);
            System.out.println(">".repeat(50));
            System.out.println("  Start rebooting the hall.........");
            System.out.println("Rebooted successfully. ");
            System.out.println(">".repeat(50));
        }
    }
    // Method Clear History
    public static void  clearHistory(String[] histories){
        Arrays.fill(histories,"");
    }
    // Method Display On Hall
    public static void displayOneHall(String[][] hall){
        System.out.println(">".repeat(50));
        loopHallEvent(hall);
    }
    // Method LoopHall Event
    public static  void loopHallEvent(String[][] hall){
     for(int i =0;i<hall.length;i++){
         for(int j=0;j<hall[i].length;j++){
             char alphabet =   (char) ('A' + 1);
             System.out.print("  |"+alphabet+"-"+(1+j)+"::"+hall[i][j]+"|\t");
         }
         System.out.println();
     }
        System.out.println(">".repeat(50));
    }

    // Method Choose Seat
    public static String[] singleAndMultipleSelect(){
        Scanner input = new Scanner(System.in);
        // Input String Dynamically
        while (true){
            System.out.print("""
                    # SCHEDULE
                    # SINGLE : C-1
                    # Multiple  Separate by comma (,) : C-1 , C-2
                    """);
            String userInput =  validateInputString("->Please select available :","# !! Please input base on Rule !","([a-zA-Z]-[1-9],)*[a-zA-Z]-[1-9]",input).toUpperCase();
            // Split the input string  by commas
            String[] substrings = userInput.split(",");
            //Check if the user wants to stop (Expressing Enter Key)
            if(!(userInput.isEmpty())){
                return  substrings;
            }

        }
    }
    // Method  Show All Hall
    public static void showAllHall(String[][] morningHall,String[][] afternoonHall,String[][]eveningHall){
        System.out.println("(..........Information about schedule..........) ");
        System.out.println(">".repeat(50));
        // Morning
        System.out.println("# Hall = Mornings ");
        loopHallEvent(morningHall);
        // Afternoon
        System.out.println("# Hall = Afternoons");
        loopHallEvent(afternoonHall);
        // Evening
        System.out.println("# Hall = Evenings ");
        loopHallEvent(eveningHall);

    }

}
