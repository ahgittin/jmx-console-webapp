package io.cloudsoft.jmxconsole.control;

/**
 * A simple tuple of an mbean operation name,
 * index, sigature, args and operation result.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81038 $
 */
public class OpResultInfo
{
   public String   name;
   public String[] signature;
   public String[] args;
   public Object   result;
   public Object   error = null;

   public OpResultInfo() {
   }

   public OpResultInfo(String name, String[] signature, String[] args, Object result)
   {
      this.name      = name;
      this.signature = signature;
      this.args      = args;
      this.result    = result;
   }

   public OpResultInfo(String name, String[] signature, String[] args, Object result, Exception e) {
       this(name, signature, args, result);
       this.error = e;
   }
}
