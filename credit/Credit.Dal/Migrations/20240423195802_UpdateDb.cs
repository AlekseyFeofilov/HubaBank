using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Credit.Dal.Migrations
{
    /// <inheritdoc />
    public partial class UpdateDb : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.EnsureSchema(
                name: "credit");

            migrationBuilder.RenameTable(
                name: "Payments",
                schema: "credit_db",
                newName: "Payments",
                newSchema: "credit");

            migrationBuilder.RenameTable(
                name: "CreditTerms",
                schema: "credit_db",
                newName: "CreditTerms",
                newSchema: "credit");

            migrationBuilder.RenameTable(
                name: "Credits",
                schema: "credit_db",
                newName: "Credits",
                newSchema: "credit");

            migrationBuilder.RenameColumn(
                name: "AccountsPayable",
                schema: "credit",
                table: "Credits",
                newName: "CurrentAccountsPayable");

            migrationBuilder.AddColumn<long>(
                name: "ArrearsInterest",
                schema: "credit",
                table: "Payments",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.AddColumn<long>(
                name: "Interest",
                schema: "credit",
                table: "Payments",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ArrearsInterest",
                schema: "credit",
                table: "Payments");

            migrationBuilder.DropColumn(
                name: "Interest",
                schema: "credit",
                table: "Payments");

            migrationBuilder.EnsureSchema(
                name: "credit_db");

            migrationBuilder.RenameTable(
                name: "Payments",
                schema: "credit",
                newName: "Payments",
                newSchema: "credit_db");

            migrationBuilder.RenameTable(
                name: "CreditTerms",
                schema: "credit",
                newName: "CreditTerms",
                newSchema: "credit_db");

            migrationBuilder.RenameTable(
                name: "Credits",
                schema: "credit",
                newName: "Credits",
                newSchema: "credit_db");

            migrationBuilder.RenameColumn(
                name: "CurrentAccountsPayable",
                schema: "credit_db",
                table: "Credits",
                newName: "AccountsPayable");
        }
    }
}
