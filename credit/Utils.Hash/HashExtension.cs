using System.Security.Cryptography;
using System.Text;

namespace Utils.Hash;

public static class HashExtension
{
    public static string GetHash(this string input)
    {

        // Convert the input string to a byte array and compute the hash.
        var data = SHA256.HashData(Encoding.UTF8.GetBytes(input));

        // Create a new Stringbuilder to collect the bytes
        // and create a string.
        var sBuilder = new StringBuilder();

        // Loop through each byte of the hashed data
        // and format each one as a hexadecimal string.
        foreach (var @byte in data)
        {
            sBuilder.Append(@byte.ToString("x2"));
        }

        // Return the hexadecimal string.
        return sBuilder.ToString();
    }

    public static bool VerifyHash(this string input, string hash)
    {
        using var hashAlgorithm = SHA256.Create();
        
        // Hash the input.
        var hashOfInput = GetHash(input);

        // Create a StringComparer an compare the hashes.
        var comparer = StringComparer.OrdinalIgnoreCase;

        return comparer.Compare(hashOfInput, hash) == 0;
    }
}