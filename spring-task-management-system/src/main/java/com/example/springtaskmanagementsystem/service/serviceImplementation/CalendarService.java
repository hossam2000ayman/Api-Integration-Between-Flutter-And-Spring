package com.example.springtaskmanagementsystem.service.serviceImplementation;

import com.example.springtaskmanagementsystem.model.TaskItem;
import com.example.springtaskmanagementsystem.repository.TaskItemRepository;
import com.example.springtaskmanagementsystem.util.GoogleCalendarTimerProvider;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Task Calendar";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private final Calendar calendar;
    private final GoogleCalendarTimerProvider timerProvider;
    private final TaskItemRepository taskItemRepository;

    public CalendarService(Calendar calendar, GoogleCalendarTimerProvider timerProvider,
                           TaskItemRepository taskItemRepository) {
        this.calendar = calendar;
        this.timerProvider = timerProvider;
        this.taskItemRepository = taskItemRepository;
    }


    public void sendAuthorizedAPI(TaskItem taskItem) throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        DateTime selectedDate = new DateTime(timerProvider.convertStringToDate(taskItem.getStartDate(), taskItem.getHour(), taskItem.getMinute(), taskItem.getSecond()));
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(selectedDate)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
    }


    public void sendAuthorizedAPI() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        // List the next 10 events from the primary calendar.
        DateTime selectedDate = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(selectedDate)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = CalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public void createEvent(TaskItem taskItem) throws GeneralSecurityException, IOException {

        Event event = new Event()
                .setSummary(taskItem.getTitle())
                .setLocation(taskItem.getLocation())
                .setDescription(taskItem.getDescription());

        LocalDateTime startDateTime = timerProvider.convertStringToDateTime(taskItem.getStartDate(), taskItem.getHour(), taskItem.getMinute(), taskItem.getSecond());

        //this minus 5 day cause of my time zone
        EventDateTime start = timerProvider.convertTo(startDateTime.minusHours(5));
        start.setTimeZone("Africa/Cairo");
        event.setStart(start);

        //this minus 5 day cause of my time zone
        EventDateTime end = timerProvider.convertTo(startDateTime.minusHours(5).plusHours(taskItem.getDurationInHour()));
        end.setTimeZone("Africa/Cairo");
        event.setEnd(end);


        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;"};
        event.setRecurrence(Arrays.asList(recurrence));


        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail("hossam2000.albadry@gmail.com"),
                new EventAttendee().setEmail("hhasanelbadry2000@gmail.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = calendar.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());

    }


    public void getAllEvents() throws IOException, GeneralSecurityException {

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = calendar.events().list("primary")
                .setMaxResults(20)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        events.getItems().forEach(event -> {
            String message = String.format("Title: %s \nDescription: %s", event.getSummary(), event.getOrganizer());
            System.out.println(message);
        });
    }


}
