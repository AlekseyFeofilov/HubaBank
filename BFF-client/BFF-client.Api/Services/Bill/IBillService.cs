using BFF_client.Api.Database.Entities;

namespace BFF_client.Api.Services.Bill
{
    public interface IBillService
    {
        Task<List<BillEntity>> GetKnownUserBills(Guid userId);

        Task<bool> GetIsBillHidden(Guid userId, Guid billId);

        Task SetIsBillHidden(Guid userId, Guid billId, bool isBillHidden);

        Task DeleteBill(Guid userId, Guid billId);
    }
}
