using Credit.IntegrationTests;
using Credit.IntegrationTests.TestModules;

var creditTestModule = new CreditTestModule();
await creditTestModule.Test();
Console.WriteLine("Success");