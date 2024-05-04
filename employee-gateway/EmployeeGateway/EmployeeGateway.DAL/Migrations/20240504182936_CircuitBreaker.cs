using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace EmployeeGateway.DAL.Migrations
{
    /// <inheritdoc />
    public partial class CircuitBreaker : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "CircuitBreakers",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uuid", nullable: false),
                    MicroserviceName = table.Column<int>(type: "integer", nullable: false),
                    CircuitBreakerStatus = table.Column<int>(type: "integer", nullable: false),
                    ErrorCount = table.Column<int>(type: "integer", nullable: false),
                    RequestCount = table.Column<int>(type: "integer", nullable: false),
                    OpenTime = table.Column<DateTime>(type: "timestamp with time zone", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CircuitBreakers", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uuid", nullable: false),
                    MessagingToken = table.Column<string>(type: "text", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CircuitBreakers");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
