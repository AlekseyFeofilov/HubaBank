using BFF_client.Api.Database;
using BFF_client.Api.Database.Entities;
using Microsoft.EntityFrameworkCore;

namespace BFF_client.Api.Services.Bill
{
    public class BillService : IBillService
    {
        private readonly AppDbContext _dbContext;

        public BillService(AppDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<List<BillEntity>> GetKnownUserBills(Guid userId)
        {
            return await _dbContext.Bills.Where(b => b.UserId == userId).ToListAsync();
        }

        public async Task<bool> GetIsBillHidden(Guid userId, Guid billId)
        {
            var bill = await _dbContext.Bills.Where(b => b.BillId == billId && b.UserId == userId).FirstOrDefaultAsync();
            return bill?.IsHidden ?? false;
        }

        public async Task SetIsBillHidden(Guid userId, Guid billId, bool isBillHidden)
        {
            var bill = await _dbContext.Bills.Where(b => b.BillId == billId && b.UserId == userId).FirstOrDefaultAsync();
            if (bill != null)
            {
                bill.IsHidden = isBillHidden;
            }
            else
            {
                await _dbContext.AddAsync(new BillEntity { UserId = userId, BillId = billId, IsHidden = isBillHidden });
            }
            await _dbContext.SaveChangesAsync();
        }

        public async Task DeleteBill(Guid userId, Guid billId)
        {
            var bill = await _dbContext.Bills.Where(b => b.BillId == billId && b.UserId == userId).FirstOrDefaultAsync();
            if (bill != null)
            {
                _dbContext.Bills.Remove(bill);
                await _dbContext.SaveChangesAsync();
            }
        }
    }
}
