using Credit.IntegrationTests.TestModules;

var creditTestModule = new CreditTestModule();
var creditTermsTestModule = new CreditTermsTestModule();

// await creditTestModule.Test();
await creditTermsTestModule.Test();

Console.WriteLine("Success");