package io.cloudsoft.jmxconsole.control;

import java.beans.PropertyEditor;

/** A simple tuple of an mbean operation name, sigature and result.

@author Scott.Stark@jboss.org
@version $Revision: 81038 $
 */
public class AttrResultInfo
{
   public String name;
   public PropertyEditor editor;
   public Object result;
   public Throwable throwable;

   public AttrResultInfo(String name, PropertyEditor editor, Object result, Throwable throwable)
   {
      this.name = name;
      this.editor = editor;
      this.result = result;
      this.throwable = throwable;
   }

   public String getAsText()
   {
      if (throwable != null)
      {
         return throwable.toString();
      }
      if( result != null )
      {
         try 
         {
            if( editor != null )
            {
               editor.setValue(result);
               return editor.getAsText();
            }
            else
            {
               return result.toString();
            }
         }
         catch (Exception e)
         {
            return "String representation of " + name + "unavailable";
         } // end of try-catch
      }
      return null;
   }
}
