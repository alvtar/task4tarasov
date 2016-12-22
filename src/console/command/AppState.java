package console.command;

public abstract class AppState {
    private static int currentUserId = 0;
    private static String currentRole = "";

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        AppState.currentUserId = currentUserId;
    }

    public static String getCurrentRole() {
        return currentRole;
    }

    public static void setCurrentRole(String currentRole) {
        AppState.currentRole = currentRole;
    }

}
