using EmployeeGateway.Common.ServicesInterface;
using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace EmployeeGateway.BL.Services;

public class FirebaseNotificationService: IFirebaseNotificationService
{
    public async Task SendNotificationAsync(string deviceToken, string title, string body)
    {
        FirebaseApp.Create(new AppOptions
        {
            Credential = GoogleCredential.FromFile(Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "firebaseConfig.json")),
        });

        var message = new Message
        {
            Notification = new Notification
            {
                Title = title,
                Body = body,
            },
            Token = deviceToken
        };

        try
        {
            var response = await FirebaseMessaging.DefaultInstance.SendAsync(message);
            Console.WriteLine($"Successfully sent message: {response}");
        }
        catch (Exception e)
        {
            Console.WriteLine($"Error sending message: {e.Message}");
        }
    }
}