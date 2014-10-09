using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
namespace Info
{
    public class Info
    {
        private int _id;
        // proprietate clasica .NET 
        public int ID
        {
            get { return _id; }
            set
            {
                if (value < 0)
                    throw new InvalidOperationException("Value must be positive!");
                else
                    _id = value;
            }

        }
        // proprietate automata 
        public string Description { get; set; }
        public DateTime Date { get; set; }
        public Info(int id, string description, DateTime date)
        {
            this.ID = id;
            this.Description = description;
            this.Date = date;
        }
        public void WriteInfo()
        {
            Console.WriteLine("Info object is:");
            Console.WriteLine("ID = {0}", ID);
            Console.WriteLine("Description = {0}", Description);
            Console.WriteLine("Date = {0}", Date);
        }


    }
}