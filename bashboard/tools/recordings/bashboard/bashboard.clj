{:config {:order 0, :description "Basic add of sites and bookings", :name :bashboard}
 :data
 [
  :break
  [:node-create [] :map]
  [:node-create [:main] :map]
  [:node-create [:main :counter] :map]
  [:transform-enable [:main :counter] :inc [{:io.pedestal.app.messages/topic [:counter]}]]
  [:node-create [:main :sites] :map]
  [:transform-enable [:main :sites] :empty [{:io.pedestal.app.messages/topic [:sites "zadev1" :bookings]}]]
  :break
  [:node-create [:main :sites-count] :map]
  [:value [:main :sites-count] nil 1]
  [:node-create [:main :free-sites] :map]
  [:value [:main :free-sites] nil ("zadev1")]
  [:node-create [:main :sites "zadev1"] :map]
  :break
  [:node-create [:main :sites "zadev2"] :map]
  [:value [:main :sites-count] 1 2]
  [:value [:main :free-sites] ("zadev1") ("zadev1" "zadev2")]
  :break
  [:node-create [:main :sites "zadev3"] :map]
  [:value [:main :sites-count] 2 3]
  [:value [:main :free-sites] ("zadev1" "zadev2") ("zadev1" "zadev2" "zadev3")]
  :break
  [:node-create [:main :sites "zadev4"] :map]
  [:value [:main :sites-count] 3 4]
  [:value [:main :free-sites] ("zadev1" "zadev2" "zadev3") ("zadev1" "zadev2" "zadev3" "zadev4")]
  :break
  [:value [:main :sites "zadev2"] nil {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]}]
  [:value [:main :free-sites] ("zadev1" "zadev2" "zadev3" "zadev4") ("zadev1" "zadev3" "zadev4")]
  :break
  [:value [:main :sites "zadev1"] nil {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]}]
  [:value [:main :free-sites] ("zadev1" "zadev3" "zadev4") ("zadev3" "zadev4")]
  :break
  [:value [:main :sites "zadev3"] nil {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]}]
  [:value [:main :free-sites] ("zadev3" "zadev4") ("zadev4")]
  :break
  [:value [:main :sites "zadev1"] {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]} {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"} {:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]}]
  :break
  [:value [:main :sites "zadev2"] {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]} {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"} {:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]}]
  :break
  [:value [:main :free-sites] ("zadev4") ("zadev1" "zadev4")]
  [:value [:main :sites "zadev1"] {:bookings [{:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"} {:owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]} {:bookings []}]
 ]}