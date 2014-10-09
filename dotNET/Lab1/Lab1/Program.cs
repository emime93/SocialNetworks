using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Info;

namespace Lab1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Write("Hello world :)");
            UsingInfoType();
            for (int i = 0; i < args.Length; ++i)
            {
                Console.WriteLine(args[i]);
            }
            string[] argumente = Environment.GetCommandLineArgs();

            foreach (string s in argumente)
                Console.WriteLine(s);

            Console.ReadKey();
            //decompilator free ilspy

            //Client: id, nume, comenzi
            //Comanda: id, Data, client, valoare
        }

        public static void UsingInfoType()
        {
            Info.Info info1 = new Info.Info(2, "Description 2", DateTime.Now);
            info1.WriteInfo();
        }
    }
}
