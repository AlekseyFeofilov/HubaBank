using System.Collections.Concurrent;

namespace BFF_client.Api.HubaWebSocket
{
    public interface IWebSocketUserDb
    {
        void AddConnection(Guid userId, SocketUserModel socketUser);

        SocketUserModel? IsExists(Guid userId, Guid billId);

        void RemoveConnection(Guid userId);
    }

    public class WebSocketUserDb : IWebSocketUserDb
    {
        private ConcurrentDictionary<Guid, SocketUserModel> userBillDictionary = new ConcurrentDictionary<Guid, SocketUserModel>();

        public void AddConnection(Guid userId, SocketUserModel socketUser)
        {
            userBillDictionary.AddOrUpdate(userId, socketUser, (key, oldValue) => socketUser);
        }

        public SocketUserModel? IsExists(Guid userId, Guid billId)
        {
            userBillDictionary.TryGetValue(userId, out SocketUserModel? socketUser);
            if (socketUser != null && socketUser.BillId == billId)
            {
                return socketUser;
            }
            return null;
        }

        public void RemoveConnection(Guid userId)
        {
            userBillDictionary.Remove(userId, out _);
        }
    }
}
