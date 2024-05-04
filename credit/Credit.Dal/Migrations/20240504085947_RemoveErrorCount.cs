using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Credit.Dal.Migrations
{
    /// <inheritdoc />
    public partial class RemoveErrorCount : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ErrorCount",
                schema: "credit",
                table: "CircuitBreakers");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "ErrorCount",
                schema: "credit",
                table: "CircuitBreakers",
                type: "integer",
                nullable: false,
                defaultValue: 0);
        }
    }
}
