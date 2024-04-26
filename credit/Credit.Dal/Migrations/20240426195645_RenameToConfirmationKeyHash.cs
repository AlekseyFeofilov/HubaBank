using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Credit.Dal.Migrations
{
    /// <inheritdoc />
    public partial class RenameToConfirmationKeyHash : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ConfirmationKey",
                schema: "credit",
                table: "IdempotentRequests",
                newName: "ConfirmationKeyHash");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ConfirmationKeyHash",
                schema: "credit",
                table: "IdempotentRequests",
                newName: "ConfirmationKey");
        }
    }
}
