using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Credit.Dal.Migrations
{
    public partial class AddPayment : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Payments",
                schema: "credit_db",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uuid", nullable: false),
                    CreditId = table.Column<Guid>(type: "uuid", nullable: false),
                    PaymentStatus = table.Column<int>(type: "integer", nullable: false),
                    PaymentDay = table.Column<DateOnly>(type: "date", nullable: false),
                    PaymentAmount = table.Column<long>(type: "bigint", nullable: false),
                    Arrears = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Payments", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Payments_Credits_CreditId",
                        column: x => x.CreditId,
                        principalSchema: "credit_db",
                        principalTable: "Credits",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Payments_CreditId",
                schema: "credit_db",
                table: "Payments",
                column: "CreditId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Payments",
                schema: "credit_db");
        }
    }
}
